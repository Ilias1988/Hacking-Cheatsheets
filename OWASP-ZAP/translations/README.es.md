# ⚡ OWASP ZAP - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy - Herramienta gratuita de pruebas de seguridad
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Seguridad web">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Código abierto">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Gratuito">
</p>

<p align="center">
  <b>🔒 El escáner gratuito de seguridad web más utilizado del mundo</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Descripción de la interfaz](#-descripción-de-la-interfaz)
- [Configuración del proxy](#-configuración-del-proxy)
- [Pruebas manuales](#-pruebas-manuales)
- [Spider y rastreadores](#-spider-y-rastreadores)
- [Escaneo activo](#-escaneo-activo)
- [Escaneo pasivo](#-escaneo-pasivo)
- [Contextos y sesiones](#-contextos-y-sesiones)
- [Fuzzer](#-fuzzer)
- [API y automatización](#-api-y-automatización)
- [Línea de comandos](#-línea-de-comandos)
- [Complementos](#-complementos)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**OWASP ZAP** (Zed Attack Proxy) es una herramienta gratuita y de código abierto para pruebas de penetración, descrita en el original como un proyecto mantenido por OWASP. Está diseñada para detectar vulnerabilidades de seguridad en aplicaciones web.

> **Actualización:** ZAP dejó de ser un proyecto de OWASP en agosto de 2023. La denominación histórica se conserva para mantener la correspondencia con el original; véase la [aclaración oficial del proyecto](https://www.zaproxy.org/docs/nowaspzap/).

### ¿Por qué utilizar ZAP?

| Función | Descripción |
|---------|-------------|
| **Gratuito y de código abierto** | Sin costes de licencia, impulsado por la comunidad |
| **Fácil de usar** | Accesible para principiantes, con funciones avanzadas |
| **Escaneo automatizado** | Spider y escaneo activo/pasivo |
| **Pruebas manuales** | Proxy de interceptación y fuzzer |
| **Compatibilidad con API** | API REST completa para automatización |
| **Multiplataforma** | Windows, Linux, macOS, Docker |
| **Ampliable** | Marketplace con más de 100 complementos |

### ZAP frente a Burp Suite

| Función | ZAP | Burp Suite |
|---------|-----|------------|
| **Precio** | Gratuito | $449+/año |
| **Código abierto** | ✅ Sí | ❌ No |
| **Escaneo automatizado** | ✅ Completo | Solo Pro |
| **Acceso a la API** | ✅ Gratuito | Solo Pro |
| **Desarrollo activo** | ✅ Sí | ✅ Sí |
| **Comunidad** | OWASP | PortSwigger |

---

> **Comparación histórica:** el precio `$449+/year` y la lista de funciones de Burp Suite reflejan el original; no constituyen una tarifa ni una comparación actualizadas.

## 📥 Instalación

### Windows
```bash
# Descargar desde el sitio oficial
https://www.zaproxy.org/download/

# O utilizar Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (preinstalado)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Descargar el DMG desde el sitio oficial
```

### Docker
```bash
# Descargar la imagen más reciente
docker pull ghcr.io/zaproxy/zaproxy:stable

# Ejecutar ZAP en modo servicio (daemon)
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Ejecutar con interfaz gráfica (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Comprobar la instalación
```bash
# Iniciar ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-descripción-de-la-interfaz"></a>
## 🖥️ Descripción de la interfaz

### Paneles principales

| Panel | Descripción |
|-------|-------------|
| **Sites Tree** | Vista jerárquica de los sitios visitados |
| **History** | Todas las peticiones y respuestas |
| **Alerts** | Vulnerabilidades detectadas |
| **Active Scan** | Progreso del escaneo actual |
| **Spider** | URL rastreadas |
| **Search** | Buscar en todos los datos |
| **Request/Response** | Ver los detalles |

### Iconos de la barra de herramientas

| Icono | Función |
|------|----------|
| 🌐 | Escaneo desde Quick Start |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Interrupción e interceptación |
| 📊 | Informes |
| ⚙️ | Opciones |

### Modos

| Modo | Descripción |
|------|-------------|
| **Safe** | Sin operaciones peligrosas |
| **Protected** | Escanear únicamente objetivos dentro del alcance |
| **Standard** | Escaneo completo (predeterminado) |
| **ATTACK** | Escaneo agresivo |

---

> **Lectura de la interfaz:** las rutas de menús, nombres de paneles, reglas y opciones se conservan en inglés para poder localizarlos en ZAP. `Right-click` indica hacer clic derecho; `Settings` son los ajustes y `Options` las opciones. Los nombres y rutas pueden variar según la versión y el idioma de la interfaz.

## 🔌 Configuración del proxy

### Configuración del navegador

**Proxy predeterminado de ZAP:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Configuración de Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Marcar "Also use this proxy for HTTPS"
4. O utilizar la extensión FoxyProxy
```

### Importar el certificado de ZAP
```
1. En el navegador, abrir: http://zap
2. Pulsar "Download ZAP Root CA Certificate"
3. Importar en el navegador:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

> **Ruta actual del proxy:** el original utiliza `Tools → Options → Local Proxies`; las versiones con el complemento Network lo configuran en `Options → Network → Local Servers/Proxies`. Consulta la [guía oficial de proxies](https://www.zaproxy.org/docs/desktop/start/proxies/).

### Configurar las opciones del proxy de ZAP
```
Tools → Options → Local Proxies

Configuración:
- Address: localhost
- Port: 8080
- Behind NAT: activar si es necesario
- Remove unsupported encodings: activar
```

### Proxy de salida (cadena de proxies)
```
Tools → Options → Network → Connection

Utilizar una cadena de proxies:
- Address: corporate-proxy.com
- Port: 8080
- Autenticación si se requiere
```

---

## 🔧 Pruebas manuales

### Interceptar peticiones (Break)

```
1. Pulsar el botón verde "Break" (🛑) de la barra de herramientas
2. Navegar por la aplicación
3. Modificar la petición interceptada
4. Pulsar "Submit and Continue" o "Drop"

Opciones de interrupción:
- Interrumpir todas las peticiones
- Interrumpir todas las respuestas
- Interrumpir según un patrón de URL concreto
```

### Editor de peticiones

```
Right-click request → Open/Resend with Request Editor

Funciones:
- Modificar cualquier parte de la petición
- Añadir o eliminar cabeceras
- Cambiar el método HTTP
- Enviar y ver la respuesta
```

### Petición manual
```
Tools → Manual Request Editor

O bien: Ctrl+M

Crear peticiones personalizadas desde cero
```

### Codificación, decodificación y hashes
```
Tools → Encode/Decode/Hash

Compatible con:
- Codificación y decodificación URL
- Codificación y decodificación Base64
- Codificación y decodificación HTML
- Codificación y decodificación hexadecimal
- Cálculo de hashes MD5, SHA1 y SHA256
```

### Funciones de búsqueda
```
Search tab → Enter pattern

Buscar en:
- URL
- Peticiones
- Respuestas
- Cabeceras
- Todo
```

---

<a id="-spider-y-rastreadores"></a>
## 🕷️ Spider y rastreadores

### Spider estándar

```
Right-click target → Spider → Spider...

O bien: Ctrl+Alt+S

Opciones:
- Profundidad máxima de rastreo
- Número máximo de elementos hijos
- Duración máxima
- Procesar parámetros
```

### Opciones del Spider
```
Tools → Options → Spider

Configuración:
- Max Depth: 5-10 (predeterminado: 5)
- Max Children: 10
- Max Duration: 0 (sin límite)
- Thread Count: 5
- Handle forms: Yes
- Post forms: Yes
```

### AJAX Spider

```
Para aplicaciones con uso intensivo de JavaScript:

Right-click target → AJAX Spider → AJAX Spider...

Requiere un navegador:
- Firefox (recomendado)
- Chrome
- HtmlUnit (sin interfaz gráfica)
```

### Opciones del AJAX Spider
```
Tools → Options → AJAX Spider

Configuración:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (sin límite)
- Max Duration: 60 minutos
- Hacer clic en los elementos predeterminados
- Hacer clic en elementos con: onclick, etc.
```

---

## ⚡ Escaneo activo

### Iniciar un escaneo activo

```
Right-click target → Active Scan...

O bien: Ctrl+Alt+A

Configurar:
- Starting Point
- Context (opcional)
- User (para la autenticación)
- Policy
- Technology
```

### Políticas de escaneo

```
Analyze → Scan Policy Manager

Crear o modificar políticas:
- Qué pruebas se ejecutarán
- Threshold (umbral de sospecha)
- Strength (intensidad)
```

### Categorías de las políticas

| Categoría | Pruebas |
|----------|-------|
| **Injection** | SQL, comandos del sistema operativo y LDAP |
| **Client Browser** | XSS, DOM XSS |
| **Server Security** | Inclusión remota de archivos |
| **Information** | Recorrido de rutas y divulgación de información |
| **Miscellaneous** | Redirecciones controladas por el usuario |

### Intensidad del escaneo

| Nivel | Descripción |
|-------|-------------|
| **Low** | Pocas peticiones, rápido |
| **Medium** | Equilibrado |
| **High** | Muchas peticiones, exhaustivo |
| **Insane** | Máximo de pruebas (lento) |

### Selección de tecnologías
```
Right-click target → Technology → Select...

Filtrar los escaneos por tecnología:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-escaneo-pasivo"></a>
## 👁️ Escaneo pasivo

### ¿Qué es el escaneo pasivo?

Analiza el tráfico sin enviar peticiones adicionales.

```
Automático: se ejecuta sobre el tráfico que pasa por el proxy

Detecta:
- Cabeceras de seguridad ausentes
- Divulgación de información
- Problemas con las cookies
- Configuración débil de SSL/TLS
- Divulgación de direcciones IP privadas
```

### Reglas de escaneo pasivo

```
Analyze → Scan Policy Manager → Passive Scan

Entre las reglas se incluyen:
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

### Configurar el escaneo pasivo
```
Tools → Options → Passive Scan

Configuración:
- Scan only in scope: recomendado
- Max alerts per rule: 10
- Tags: activar
```

---

## 🎯 Contextos y sesiones

### ¿Qué son los contextos?

Agrupaciones lógicas de URL relacionadas que permiten organizar las pruebas.

### Crear un contexto
```
Right-click Sites → Include in Context → New Context

O bien: File → New Context
```

### Configuración del contexto

| Ajuste | Finalidad |
|---------|---------|
| **Include in Context** | URL que se incluirán |
| **Exclude from Context** | URL que se excluirán |
| **Technology** | Tecnología del servidor |
| **Authentication** | Método de inicio de sesión |
| **Session Management** | Gestión de cookies y tokens |
| **Users** | Cuentas de usuario para las pruebas |

### Tipos de autenticación

| Tipo | Caso de uso |
|------|----------|
| **Form-based** | Formularios de inicio de sesión |
| **HTTP Basic** | Autenticación Basic |
| **JSON-based** | Autenticación de API |
| **Script-based** | Autenticación personalizada |

### Autenticación mediante formularios
```
1. Registrar la petición de inicio de sesión en ZAP
2. Right-click → Include in Context
3. Context → Authentication → Form-based
4. Configurar Login URL
5. Configurar Login Request POST Data
6. Configurar los parámetros de usuario y contraseña
7. Configurar los indicadores de sesión iniciada y cerrada
```

### Gestión de sesiones
```
Context → Session Management

Tipos:
- Cookie-based (el más habitual)
- HTTP Auth
- Script-based
```

### Usuarios
```
Context → Users → Add

Añadir usuarios de prueba:
- Name: admin
- Credentials: admin/password123
```

---

## 🎲 Fuzzer

### Iniciar el fuzzer
```
1. Seleccionar una petición en History
2. Right-click → Attack → Fuzz...
3. Resaltar la posición para fuzzing
4. Añadir payloads
5. Iniciar
```

### Añadir una posición para fuzzing
```
1. En el diálogo Fuzzer, resaltar el texto al que se aplicará fuzzing
2. Pulsar "Add..."
3. Seleccionar el tipo de payload
```

### Tipos de payloads

| Tipo | Descripción |
|------|-------------|
| **Strings** | Lista personalizada de cadenas |
| **File** | Cargar desde un archivo |
| **File Fuzzers** | Listas de palabras incluidas |
| **Numbers** | Secuencias de números |
| **Regex** | Generados a partir de expresiones regulares |
| **Script** | Script personalizado |

### Fuzzers incluidos
```
Add → File Fuzzers

Categorías:
- jbrofuzz (XSS, SQL, etc.)
- dirbuster (directorios)
- fuzzdb (colección amplia)
```

### Opciones del fuzzer
```
Opciones:
- Hilos concurrentes
- Espera entre peticiones
- Seguir las redirecciones
- Mostrar todos los resultados o aplicar filtros
```

### Ejemplo de fuzzer: fuzzing de parámetros
```
1. Capturar: GET /page?id=1
2. Right-click → Fuzz
3. Resaltar "1"
4. Añadir payloads de inyección SQL
5. Iniciar el fuzzer
6. Analizar los resultados por longitud y código de la respuesta
```

---

## 🤖 API y automatización

### Activar la API
```
Tools → Options → API

Configuración:
- Enabled: Yes
- API Key: (generada automáticamente o personalizada)
- Permitted addresses: localhost
```

### Acceso a la API
```bash
# Raíz de la API
http://localhost:8080/JSON/

# Con clave de API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Obtener la versión
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Endpoints habituales de la API

| Endpoint | Finalidad |
|----------|---------|
| `/core/view/alerts/` | Obtener todas las alertas |
| `/core/view/hosts/` | Obtener todos los hosts |
| `/spider/action/scan/` | Iniciar el Spider |
| `/ascan/action/scan/` | Iniciar un escaneo activo |
| `/core/view/urls/` | Obtener todas las URL |
| `/core/action/shutdown/` | Cerrar ZAP |

### Ejemplos de la API

```bash
# Rastrear una URL
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Comprobar el estado del rastreo
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Iniciar un escaneo activo
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Obtener las alertas
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Generar un informe HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

> **Secuencia de automatización:** las llamadas que inician el Spider y el escaneo activo son asíncronas. El ejemplo Python conserva el flujo abreviado del original; para obtener resultados completos hay que esperar a que termine el rastreo, el análisis pasivo y el escaneo activo. Véase la [documentación de la API](https://www.zaproxy.org/docs/api/).

### Cliente Python de la API
```python
from zapv2 import ZAPv2

# Conectarse a ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Active Scan
zap.ascan.scan(url='http://target.com')

# Obtener las alertas
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-línea-de-comandos"></a>
## ⌨️ Línea de comandos

### Opciones básicas de la línea de comandos
```bash
# Start ZAP in daemon mode
zap.sh -daemon

# Iniciar en un puerto concreto
zap.sh -daemon -port 8090

# Escaneo rápido
zap.sh -quickurl http://target.com -quickout report.html

# Escaneo baseline (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Framework de automatización
```bash
# Ejecutar el escaneo automatizado
zap.sh -cmd -autorun automation.yaml
```

### Ejemplo de automatización en YAML
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

> **Matiz del baseline:** «solo pasivo» describe el análisis de seguridad, pero `zap-baseline.py` sí envía peticiones durante el rastreo inicial. Véase la [documentación oficial del baseline](https://www.zaproxy.org/docs/docker/baseline-scan/). Para conservar los informes fuera del contenedor, el directorio de trabajo necesita un volumen accesible desde el host.

### Escaneos con Docker

```bash
# Escaneo baseline (solo análisis pasivo)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Escaneo completo (activo)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# Escaneo de API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

## 🧩 Complementos

### Instalar complementos
```
Help → Check for Updates → Marketplace

O bien: Tools → Add-ons → Marketplace
```

### Complementos esenciales

| Complemento | Descripción |
|--------|-------------|
| **Active Scan Rules** | Reglas de escaneo adicionales |
| **Passive Scan Rules** | Reglas pasivas adicionales |
| **FuzzDB** | Payloads para fuzzing |
| **Retire.js** | Detectar bibliotecas JS vulnerables |
| **AJAX Spider** | Rastreador compatible con JavaScript |
| **Selenium** | Automatización del navegador |
| **Wappalyzer** | Detección de tecnologías |
| **Access Control** | Pruebas de control de acceso |
| **Directory List** | Listas de directorios |

### Gestión de complementos
```
# Listar los instalados
Tools → Add-ons → Installed

# Actualizar todos
Help → Check for Updates → Update All
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: escaneo web rápido
```
1. Introducir la URL en la pestaña Quick Start
2. Pulsar "Attack"
3. ZAP rastreará y escaneará automáticamente
4. Revisar las alertas al finalizar
```

### Ejemplo 2: pruebas manuales y automatizadas
```
1. Configurar el navegador para utilizar el proxy de ZAP
2. Navegar manualmente por la aplicación y explorar todas sus funciones
3. Right-click target in Sites → Spider
4. Esperar a que termine el rastreo
5. Right-click target → Active Scan
6. Revisar las alertas
7. Generar el informe
```

### Ejemplo 3: escaneo autenticado
```
1. Crear un Context para el objetivo
2. Configurar Form-based Authentication
3. Añadir las credenciales del usuario de prueba
4. Configurar el modo de usuario forzado
5. Rastrear como usuario autenticado
6. Ejecutar Active Scan
```

### Ejemplo 4: pruebas de API
```
1. Importar la definición de la API (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Rastrear la API
4. Ejecutar Active Scan
```

### Ejemplo 5: integración CI/CD
```bash
# Pipeline de Jenkins
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

## 📊 Referencia rápida

### Acciones esenciales

| Tarea | Método |
|------|--------|
| Iniciar el proxy | ZAP inicia el proxy automáticamente en el puerto 8080 |
| Spider | Right-click → Spider |
| Active Scan | Right-click → Active Scan |
| Interceptar | Pulsar el botón Break (🛑) |
| Fuzz | Right-click request → Fuzz |
| Generate Report | Report → Generate Report |

### Atajos de teclado

| Atajo | Acción |
|----------|--------|
| `Ctrl+Alt+S` | Diálogo del Spider |
| `Ctrl+Alt+A` | Diálogo de Active Scan |
| `Ctrl+M` | Petición manual |
| `Ctrl+B` | Activar o desactivar Break |
| `Ctrl+F` | Buscar |
| `Ctrl+R` | Reenviar petición |

### Modos de escaneo

| Modo | Cuándo utilizarlo |
|------|----------|
| **Safe** | Cuando no se quiera realizar ningún cambio |
| **Protected** | Cuando se pruebe únicamente un alcance concreto |
| **Standard** | Pruebas habituales |
| **ATTACK** | Pruebas agresivas (¡con autorización!) |

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Limitar el alcance**
   ```
   Definir un contexto y escanear únicamente elementos dentro del alcance
   ```

2. **Utilizar AJAX Spider para las SPA**
   ```
   Las aplicaciones modernas necesitan AJAX Spider además del rastreador estándar
   ```

3. **Configurar las tecnologías**
   ```
   Seleccionar solo las tecnologías pertinentes para reducir el tiempo de escaneo
   ```

### Consejos para mejorar la precisión

1. **Autenticarse primero**
   ```
   Configurar la autenticación para ampliar la cobertura
   ```

2. **Explorar manualmente antes de automatizar**
   ```
   Navegar primero por la aplicación para descubrir todas sus funciones
   ```

3. **Revisar todas las alertas**
   ```
   Revisar los falsos positivos y verificar los positivos verdaderos
   ```

### Flujo de trabajo

```
1. Configurar el proxy
2. Explorar manualmente (con autenticación)
3. Rastrear con Spider (estándar y AJAX)
4. Revisar el escaneo pasivo
5. Realizar el escaneo activo
6. Realizar pruebas manuales (fuzzing y casos límite)
7. Generar el informe
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** OWASP ZAP es una herramienta de pruebas de seguridad que debe utilizarse únicamente en **pruebas autorizadas**.
> 
> - ✅ Probar aplicaciones propias
> - ✅ Realizar pruebas con autorización expresa por escrito
> - ✅ Utilizar en entornos de pruebas o preproducción
> - ❌ No escanear producción sin autorización
> - ❌ No escanear sistemas ajenos
> 
> **Las pruebas de seguridad no autorizadas son ilegales y pueden dar lugar a cargos penales.**

---

## 📚 Recursos

### Recursos oficiales
- [Sitio web de OWASP ZAP](https://www.zaproxy.org/)
- [Documentación de ZAP](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [Documentación de la API de ZAP](https://www.zaproxy.org/docs/api/)

### Recursos de aprendizaje
- [Guía de pruebas de OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Vídeos avanzados de ZAP](https://www.zaproxy.org/docs/videos/)

### Guías rápidas relacionadas
- [Burp Suite](../../Burp-Suite/translations/README.es.md)
- [Nikto](../../Nikto/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)

---

<p align="center">
  <b>⚡ ¡Domina las pruebas de seguridad de aplicaciones web!</b><br>
  <i>Gratuito, potente e impulsado por la comunidad</i>
</p>
