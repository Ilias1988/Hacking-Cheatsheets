# 🔶 Burp Suite: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Compatibilidad con versiones:** Se mantienen literalmente las rutas de menús, nombres de controles, payloads y valores del original. Algunas rutas `Proxy → Options`, `Spider` y `Extender` corresponden a interfaces antiguas; véase la [configuración actual del proxy](https://portswigger.net/burp/documentation/desktop/settings/tools/proxy). El precio `$449/year` y la tabla de requisitos son datos históricos del original. Para el JAR actual, PortSwigger indica Java 21 como mínimo ([requisitos de ejecución](https://portswigger.net/burp/documentation/desktop/troubleshooting/launch-from-command-line)); los instaladores incluyen su entorno de ejecución. Los atajos son configurables y la tabla original contiene asignaciones que pueden entrar en conflicto. Una evaluación estadística de Sequencer no demuestra por sí sola seguridad criptográfica.


```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Plataforma de pruebas de seguridad de aplicaciones web
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 La plataforma de referencia del sector para probar la seguridad de aplicaciones web</b>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Instalación y configuración inicial](#instalación-y-configuración-inicial)
- [Configuración del proxy](#configuración-del-proxy)
- [Intercepción del tráfico](#intercepción-del-tráfico)
- [Objetivo y alcance](#objetivo-y-alcance)
- [Spider/Crawler](#spidercrawler)
- [Scanner](#scanner)
- [Repeater](#repeater)
- [Intruder](#intruder)
- [Decoder](#decoder)
- [Comparer](#comparer)
- [Sequencer](#sequencer)
- [Extensiones](#extensiones)
- [Atajos de teclado](#atajos-de-teclado)
- [Flujo de trabajo de pruebas](#flujo-de-trabajo-de-pruebas)
- [Referencia rápida](#referencia-rápida)
- [Consejos y buenas prácticas](#consejos-y-buenas-prácticas)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**Burp Suite** es una plataforma integrada para realizar pruebas de seguridad de aplicaciones web. Desarrollada por PortSwigger, es la herramienta de referencia del sector para pruebas de penetración web.

### Ediciones

| Edición | Funciones | Precio |
|---------|----------|-------|
| **Community** | Proxy básico y herramientas manuales | Gratuita |
| **Professional** | Scanner, Intruder (sin límite) y Collaborator | $449/year |
| **Enterprise** | Integración CI/CD y funciones de equipo | Consultar al equipo de ventas |

### Herramientas principales

| Herramienta | Descripción |
|------|-------------|
| **Proxy** | Interceptar y modificar tráfico HTTP/HTTPS |
| **Spider** | Rastrear aplicaciones web automáticamente |
| **Scanner** | Encontrar vulnerabilidades automáticamente |
| **Intruder** | Ataques automatizados personalizados |
| **Repeater** | Manipulación manual de solicitudes |
| **Decoder** | Codificar y decodificar datos |
| **Comparer** | Comparar respuestas |
| **Sequencer** | Analizar la aleatoriedad de los tokens |

---

<a id="instalación-y-configuración-inicial"></a>

## 📥 Instalación y configuración inicial

### Descarga

```bash
# Descargar desde el sitio oficial
https://portswigger.net/burp/releases

# Disponible para:
# - Windows (instalador o versión independiente)
# - macOS (DMG)
# - Linux (script o JAR)
```

### Requisitos del sistema

| Requisito | Mínimo | Recomendado |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Disco | 500 MB | 2+ GB |
| Java | JRE 8+ | JRE incluido |
| Pantalla | 1280x800 | 1920x1080 |

### Configuración del primer inicio

1. **Seleccionar el tipo de proyecto**
   - Temporary project (proyecto temporal para pruebas)
   - New project on disk (proyecto en disco para guardar el trabajo)
   - Open existing project (abrir un proyecto existente)

2. **Configuración**
   - Use Burp defaults (usar los valores predeterminados; recomendado para principiantes)
   - Load from configuration file (cargar desde un archivo de configuración)

3. **Asignación de memoria**
   ```bash
   # Aumentar la memoria para proyectos grandes
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

<a id="configuración-del-proxy"></a>

## 🔌 Configuración del proxy

### Configuración del navegador

#### Configuración manual del proxy

| Navegador | Ubicación de la configuración del proxy |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Usa el proxy del sistema o una extensión |
| Safari | System Preferences → Network → Proxies |

**Proxy predeterminado de Burp:**
```
Host: 127.0.0.1
Port: 8080
```

#### Configuración de Firefox
```
1. Abrir las preferencias de Firefox
2. Buscar "proxy"
3. Hacer clic en "Settings..."
4. Seleccionar "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. Marcar "Also use this proxy for HTTPS"
7. Hacer clic en OK
```

#### Extensión FoxyProxy (recomendada)
```
1. Instalar la extensión FoxyProxy
2. Añadir un proxy nuevo:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. Activarlo durante las pruebas
```

### Instalación del certificado SSL/TLS

```bash
# Paso 1: con el proxy activado, visitar:
http://burp

# Paso 2: hacer clic en "CA Certificate" para descargarlo

# Paso 3: importar el certificado:
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# Sistema: añadir a los certificados raíz de confianza
```

### Listeners del proxy

```
Proxy → Options → Proxy Listeners

Valor predeterminado: 127.0.0.1:8080

Añadir listeners personalizados:
- Puertos distintos para diferentes pruebas
- Escuchar en todas las interfaces (0.0.0.0) para pruebas móviles
- Redirigir a distintos hosts
```

### Proxy invisible (para clientes sin compatibilidad con proxy)

```
1. Proxy → Options → Proxy Listeners
2. Edit listener → Request handling
3. Activar "Support invisible proxying"
4. Configurar el archivo hosts o el DNS
```

---

<a id="intercepción-del-tráfico"></a>

## 🎣 Intercepción del tráfico

### Controles de intercepción

| Botón | Función |
|--------|----------|
| **Intercept is on/off** | Activar o desactivar la intercepción |
| **Forward** | Enviar la solicitud al servidor |
| **Drop** | Descartar la solicitud |
| **Action** | Menú de opciones adicionales |

### Opciones de intercepción

```
Proxy → Options → Intercept Client Requests

Ejemplos de reglas:
- Interceptar solo los elementos incluidos en el alcance
- Interceptar solo tipos de archivo específicos
- Excluir imágenes y archivos estáticos
```

### Modificación de solicitudes

```http
# Solicitud original
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Solicitud modificada (mientras está interceptada)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Buscar y reemplazar (Match and Replace)

```
Proxy → Options → Match and Replace

Reglas habituales:
- Eliminar cabeceras de seguridad
- Modificar User-Agent
- Añadir cabeceras personalizadas
- Reemplazar parámetros de solicitud
```

| Tipo | Coincidencia | Reemplazo | Finalidad |
|------|-------|---------|---------|
| Request header | `User-Agent: .*` | `User-Agent: Custom` | Cambiar User-Agent |
| Request body | `password=test` | `password=admin` | Probar credenciales |
| Response header | `X-Frame-Options.*` | ` ` | Eliminar una cabecera |
| Response body | `disabled` | `enabled` | Habilitar botones |

### Modificación de respuestas

```
Proxy → Options → Intercept Server Responses

Activar para:
- Eliminar cabeceras de seguridad
- Modificar JavaScript
- Cambiar el contenido de la respuesta
- Probar controles del lado del cliente
```

---

<a id="objetivo-y-alcance"></a>

## 🎯 Objetivo y alcance

### Añadir al alcance

```
Método 1: Right-click in Site map → Add to scope
Método 2: Target → Scope → Add (manually)
Método 3: From Proxy history → Add to scope
```

### Configuración del alcance

```
Target → Scope

Incluir en el alcance:
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: Regex pattern

Patrones de ejemplo:
.*\.target\.com$          # Todos los subdominios
^https://target\.com/app  # Ruta específica
```

### Configuración avanzada del alcance

```
Target → Scope → Advanced scope control

Include (incluir):
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclude (excluir):
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Mapa del sitio (Site map)

```
Target → Site map

Funciones:
- Ver todo el contenido descubierto
- Filtrar por alcance
- Buscar y filtrar contenido
- Resaltar los elementos de interés
- Comparar mapas del sitio
```

---

<a id="spidercrawler"></a>

## 🕷️ Spider/Crawler

### Iniciar Spider

```
1. Hacer clic con el botón derecho en el objetivo de Site map
2. Seleccionar "Spider this host" o "Spider this branch"
3. Or: Target → Site map → Right-click → Scan → Crawl
```

### Configuración de Spider

```
Spider → Options (legacy) OR
Dashboard → New scan → Crawl

Configuración del rastreador:
- Profundidad máxima de los enlaces
- Tiempo máximo de rastreo
- Envío de formularios
- Gestión del inicio de sesión
```

### Configuración del rastreo

| Ajuste | Descripción | Recomendación |
|---------|-------------|-------------|
| **Link depth** | Profundidad con la que se siguen los enlaces | 5-10 |
| **Max requests** | Límite total de solicitudes | 1000-5000 |
| **Duplicate removal** | Omitir páginas similares | Enabled (activado) |
| **Passive scanning** | Analizar durante el rastreo | Enabled (activado) |

### Envío de formularios

```
Spider → Options → Application Login

Configurar el inicio de sesión para:
- Un juego de credenciales
- Lista de credenciales
- Detección de sesiones
```

---

<a id="scanner"></a>

## 🔍 Scanner

### Tipos de análisis

| Tipo | Descripción | Uso |
|------|-------------|----------|
| **Passive** | Analizar el tráfico del proxy | Siempre activo |
| **Active** | Enviar payloads para encontrar vulnerabilidades | Con autorización |
| **Full** | Análisis completo | Cuando se disponga de tiempo |
| **Custom** | Solo pruebas específicas | Pruebas dirigidas |

### Iniciar un análisis

```
Método 1: Right-click → Scan
Método 2: Dashboard → New scan
Método 3: Usar Send to Scanner desde otras herramientas
```

### Configuración del análisis

```
Dashboard → New scan → Scan configuration

Configuraciones integradas:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Categorías de vulnerabilidades

```
Scanner comprueba:

Inyección:
- Inyección SQL
- Inyección de comandos del sistema operativo
- Inyección LDAP
- Inyección XML/XPath

Cross-Site Scripting (XSS):
- XSS reflejado
- XSS almacenado
- XSS basado en DOM

Autenticación:
- Autenticación defectuosa
- Problemas de gestión de sesiones
- Contraseñas débiles

Otros:
- CSRF
- Recorrido de directorios (directory traversal)
- Inclusión de archivos
- Divulgación de información
- Problemas del lado del servidor
```

### Resultados del análisis

```
Dashboard → Issue activity
Target → Site map → Issues

Para cada hallazgo:
- Severity (High/Medium/Low/Info)
- Confidence (Certain/Firm/Tentative)
- Descripción
- Corrección
- Evidencia (solicitud y respuesta)
```

### Perfiles de análisis personalizados

```
1. Dashboard → New scan
2. Seleccionar "Scan configuration"
3. Hacer clic en "New" o modificar una configuración existente
4. Guardar para reutilizar

Personalizar:
- Qué vulnerabilidades se deben probar
- Intensidad de los payloads
- Tasa de solicitudes
- Gestión de la autenticación
```

---

<a id="repeater"></a>

## 🔁 Repeater

### Finalidad

Pruebas manuales y manipulación de solicitudes individuales.

### Enviar a Repeater

```
1. Hacer clic con el botón derecho en cualquier solicitud
2. Seleccionar "Send to Repeater"
3. Teclado: Ctrl+R
```

### Usar Repeater

```
1. Modificar la solicitud en el panel izquierdo
2. Hacer clic en "Send" (o Ctrl+Space)
3. Ver la respuesta en el panel derecho
4. Comparar respuestas
```

### Funciones de Repeater

| Función | Descripción |
|---------|-------------|
| **Multiple tabs** | Probar varias solicitudes |
| **Request history** | Navegar con los botones < > |
| **Follow redirects** | Activar o desactivar el seguimiento automático |
| **Process cookies** | Actualizar cookies automáticamente |
| **Content-Length** | Actualizar la cabecera automáticamente |

### Ejemplos de pruebas

```http
# Probar la inyección SQL
GET /user.php?id=1' HTTP/1.1

# Probar XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Probar la evasión de autenticación
GET /admin HTTP/1.1
Cookie: role=admin

# Probar IDOR
GET /api/user/1 HTTP/1.1  # El propio usuario
GET /api/user/2 HTTP/1.1  # Otro usuario
```

---

<a id="intruder"></a>

## 🔫 Intruder

### Tipos de ataque

| Tipo | Posiciones | Payloads | Uso |
|------|-----------|----------|----------|
| **Sniper** | 1+ | Un conjunto, una posición cada vez | Pruebas de un único parámetro |
| **Battering Ram** | 1+ | Un conjunto, el mismo en todas las posiciones | El mismo valor en todas las posiciones |
| **Pitchfork** | 1+ | Varios conjuntos en paralelo | Pares de usuario y contraseña |
| **Cluster Bomb** | 1+ | Varios conjuntos, todas las combinaciones | Fuerza bruta completa de credenciales |

### Enviar a Intruder

```
1. Hacer clic con el botón derecho en la solicitud
2. Seleccionar "Send to Intruder"
3. Teclado: Ctrl+I
```

### Definir las posiciones

```
Intruder → Positions

Marcar los puntos de inyección con símbolos §:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Botones:
- Add § - Marcar la selección
- Clear § - Eliminar los marcadores
- Auto § - Detectar automáticamente
```

### Tipos de payload

| Tipo | Descripción |
|------|-------------|
| **Simple list** | Lista de palabras personalizada |
| **Runtime file** | Cargar desde un archivo |
| **Numbers** | Números secuenciales o aleatorios |
| **Dates** | Secuencias de fechas |
| **Brute forcer** | Combinaciones de caracteres |
| **Null payloads** | Payloads vacíos (pruebas de tasa de solicitudes) |
| **Username generator** | Patrones habituales de nombres de usuario |

### Procesamiento de payloads

```
Intruder → Payloads → Payload Processing

Añadir reglas:
- Añadir prefijo o sufijo
- Codificar como URL
- Codificar en Base64
- Calcular hash (MD5, SHA)
- Buscar y reemplazar
```

### Grep - Match

```
Intruder → Options → Grep - Match

Añadir cadenas que se marcarán en las respuestas:
- "Login successful"
- "Invalid password"
- "error"
- Expresión regular personalizada

Ayuda a identificar ataques que han tenido éxito.
```

### Ejemplos de ataques

#### Fuerza bruta de inicio de sesión
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Tipo de ataque: Cluster bomb
Payload 1: lista de nombres de usuario
Payload 2: lista de contraseñas
```

#### Fuzzing de parámetros
```
GET /api/user/§1§ HTTP/1.1

Tipo de ataque: Sniper
Payload: Numbers 1-1000 (secuencia de números)
```

#### Descubrimiento de contenido
```
GET /§admin§ HTTP/1.1

Tipo de ataque: Sniper
Payload: lista de palabras de directorios
Filter: Status code != 404
```

### Análisis de resultados

```
Después del ataque:
- Ordenar por longitud (encontrar respuestas diferentes)
- Ordenar por código de estado
- Filtrar por las coincidencias de grep
- Exportar los resultados
```

---

<a id="decoder"></a>

## 🔓 Decoder

### Codificación y decodificación

```
Pestaña Decoder

Operaciones:
- Codificación y decodificación URL
- Codificación y decodificación HTML
- Codificación y decodificación Base64
- Codificación y decodificación hexadecimal
- Compresión y descompresión Gzip
- ASCII hexadecimal
- Binario
```

### Smart Decode

```
Decoder → Smart decode

Detecta y decodifica automáticamente:
- Base64
- Codificación URL
- Entidades HTML
- Codificaciones combinadas
```

### Usos habituales

```
# Decodificar un token JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Decode as Base64

# Decodificar un parámetro de URL
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ URL decode
→ <script>alert(1)</script>

# Codificar un payload
<script>alert(1)</script>
→ Base64 encode
→ Usar en el ataque
```

---

<a id="comparer"></a>

## 📊 Comparer

### Finalidad

Comparar dos elementos de datos (solicitudes, respuestas o cualquier otro dato).

### Cómo usarlo

```
1. Right-click item 1 → Send to Comparer
2. Right-click item 2 → Send to Comparer
3. Ir a la pestaña Comparer
4. Seleccionar los elementos y hacer clic en "Words" o "Bytes"
```

### Modos de comparación

| Modo | Descripción |
|------|-------------|
| **Words** | Comparar palabra por palabra |
| **Bytes** | Comparar byte por byte (exacto) |

### Casos de uso

```
- Comparar respuestas de inicio de sesión correcto y fallido
- Encontrar diferencias entre tokens de sesión
- Comparar antes y después de las modificaciones
- Identificar campos ocultos de formularios
```

---

<a id="sequencer"></a>

## 🔢 Sequencer

### Finalidad

Analizar la calidad de la aleatoriedad de los tokens (identificadores de sesión y tokens CSRF).

### Cómo usarlo

```
1. Capturar una solicitud que devuelva un token
2. Right-click → Send to Sequencer
3. Configurar la ubicación del token
4. Pulsar Start live capture (iniciar la captura en vivo)
5. Analizar los resultados
```

### Métricas de análisis

| Métrica | Valor adecuado | Descripción |
|--------|------------|-------------|
| **Overall quality** | >100 bits | Puntuación de aleatoriedad |
| **Character-level** | Entropía alta | Aleatoriedad por carácter |
| **Bit-level** | Entropía alta | Aleatoriedad por bit |

### Interpretación

```
Resultados:
- Excellent: El token es criptográficamente aleatorio (interpretación del original)
- Good: Aceptable para la mayoría de usos  
- Poor: Potencialmente predecible
- Very poor: Es posible adivinar el token
```

---

<a id="extensiones"></a>

## 🧩 Extensiones

### BApp Store

```
Extender → BApp Store

Extensiones populares:
```

### Extensiones útiles

| Extensión | Descripción |
|-----------|-------------|
| **Autorize** | Pruebas de autorización |
| **Logger++** | Registro ampliado |
| **Retire.js** | Detectar bibliotecas JavaScript vulnerables |
| **Active Scan++** | Comprobaciones de análisis adicionales |
| **AuthMatrix** | Pruebas mediante una matriz de autorización |
| **Turbo Intruder** | Alternativa rápida a Intruder |
| **Param Miner** | Descubrimiento de parámetros ocultos |
| **JSON Web Tokens** | Análisis y manipulación de JWT |
| **Hackvertor** | Codificación y decodificación avanzadas |
| **CO2** | Conjunto de herramientas útiles |

### Instalar extensiones

```
Método 1: BApp Store
1. Extender → BApp Store
2. Buscar la extensión
3. Hacer clic en Install

Método 2: Manual
1. Descargar el archivo .jar o .py
2. Extender → Extensions → Add
3. Seleccionar el tipo y la ruta del archivo
```

### Configuración de extensiones

```
Extender → Options

- Entorno Python (para extensiones Python)
- Configuración del entorno Java
- Opciones de registro
```

---

<a id="atajos-de-teclado"></a>

## ⌨️ Atajos de teclado

### Atajos globales

| Atajo | Acción |
|----------|--------|
| `Ctrl+I` | Send to Intruder (enviar a Intruder) |
| `Ctrl+R` | Send to Repeater (enviar a Repeater) |
| `Ctrl+S` | Search (buscar) |
| `Ctrl+F` | Forward (Proxy) (reenviar en Proxy) |
| `Ctrl+D` | Drop (Proxy) (descartar en Proxy) |
| `Ctrl+T` | Toggle Intercept (activar o desactivar la intercepción) |
| `Ctrl+Shift+T` | Toggle Proxy intercept (activar o desactivar la intercepción del proxy) |

### Navegación entre pestañas

| Atajo | Acción |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### En Repeater

| Atajo | Acción |
|----------|--------|
| `Ctrl+Space` | Enviar la solicitud |
| `Ctrl+G` | Ir a (historial de solicitudes) |
| `Ctrl+Plus` | Nueva pestaña |

### En el editor

| Atajo | Acción |
|----------|--------|
| `Ctrl+U` | Codificar la selección como URL |
| `Ctrl+Shift+U` | Decodificar la selección como URL |
| `Ctrl+B` | Codificar en Base64 |
| `Ctrl+Shift+B` | Decodificar Base64 |

---

<a id="flujo-de-trabajo-de-pruebas"></a>

## 🎯 Flujo de trabajo de pruebas

### Flujo habitual de pruebas de aplicaciones web

```
Paso 1: configuración
├── Configurar el proxy del navegador
├── Instalar el certificado SSL
└── Definir el alcance

Paso 2: reconocimiento
├── Navegar manualmente por la aplicación
├── Ejecutar Spider/Crawler
└── Revisar Site map

Paso 3: análisis
├── Comprobar el historial del proxy
├── Revisar los resultados del análisis pasivo
└── Identificar la superficie de ataque

Paso 4: pruebas
├── Probar con Repeater (manual)
├── Probar con Intruder (automatizado)
├── Ejecutar un análisis activo
└── Usar extensiones

Paso 5: informe
├── Revisar todos los hallazgos
├── Generar el informe
└── Documentar las evidencias
```

### Lista de comprobación de pruebas

```
Autenticación:
□ Probar el inicio de sesión mediante fuerza bruta
□ Política de contraseñas
□ Bloqueo de cuentas
□ Gestión de sesiones
□ Función para recordar al usuario

Autorización:
□ Pruebas de IDOR
□ Elevación de privilegios
□ Acceso a nivel de función

Validación de entradas:
□ Inyección SQL
□ XSS (reflejado, almacenado o DOM)
□ Inyección de comandos
□ Carga de archivos
□ XXE

Sesión:
□ Fijación de sesión
□ Caducidad de sesión
□ Aleatoriedad de los tokens

Lógica de negocio:
□ Manipulación de precios
□ Evasión del flujo de trabajo
□ Condiciones de carrera
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Resumen de herramientas

| Herramienta | Uso principal | Atajo principal |
|------|-------------|--------------|
| Proxy | Interceptar tráfico | Ctrl+T (activar o desactivar) |
| Repeater | Pruebas manuales | Ctrl+R (enviar a Repeater) |
| Intruder | Ataques automatizados | Ctrl+I (enviar a Intruder) |
| Scanner | Encontrar vulnerabilidades | Right-click → Scan |
| Spider | Descubrir contenido | Right-click → Spider |
| Decoder | Codificar y decodificar | - |
| Comparer | Comparar diferencias entre respuestas | Send to Comparer |
| Sequencer | Análisis de tokens | Send to Sequencer |

### Referencia rápida de tipos de ataque de Intruder

| Ataque | Cuándo usarlo |
|--------|----------|
| **Sniper** | Probar un parámetro cada vez |
| **Battering Ram** | El mismo payload en todas las posiciones |
| **Pitchfork** | Pares de usuario y contraseña |
| **Cluster Bomb** | Todas las combinaciones de usuario y contraseña |

### Códigos de estado habituales

| Código | Significado | Relevancia en Intruder |
|------|---------|----------------------|
| 200 | OK | Respuesta normal |
| 301/302 | Redirección | Puede indicar éxito |
| 401 | No autorizado | Credenciales incorrectas |
| 403 | Prohibido | Acceso denegado |
| 404 | No encontrado | Ruta no válida |
| 500 | Error del servidor | Posible vulnerabilidad |

---

<a id="consejos-y-buenas-prácticas"></a>

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Limitar el alcance**
   ```
   Mantener solo los dominios objetivo dentro del alcance
   Reduce el ruido en el historial del proxy
   ```

2. **Desactivar extensiones**
   ```
   Desactivar extensiones sin uso para mejorar el rendimiento
   ```

3. **Usar archivos de proyecto**
   ```
   Guardar el trabajo con frecuencia
   Usar archivos de proyecto para pruebas grandes
   ```

### Consejos para las pruebas

1. **Empezar por el análisis pasivo**
   ```
   Dejar que el escáner pasivo encuentre los problemas más evidentes
   Revisar antes del análisis activo
   ```

2. **Usar Repeater primero**
   ```
   Las pruebas manuales permiten descubrir más detalles
   Comprender la aplicación antes de automatizar
   ```

3. **Organizar mediante pestañas**
   ```
   Asignar nombres descriptivos a las pestañas de Repeater
   Usar nombres para los ataques de Intruder
   ```

### Evitar la detección

```
Configuración para reducir la visibilidad:

Project options → Connections:
- Limitar la tasa de solicitudes
- Intervalos aleatorios
- Límite de conexiones simultáneas

Intruder → Options:
- Intervalo entre solicitudes
- Hora de inicio
```

### Resolución de problemas

| Problema | Solución |
|-------|----------|
| No se puede interceptar HTTPS | Instalar el certificado de CA |
| Rendimiento lento | Reducir el alcance y desactivar extensiones |
| Faltan solicitudes | Comprobar "Intercept Server Responses" |
| Scanner no encuentra vulnerabilidades | Aumentar la intensidad del análisis |

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Burp Suite es una herramienta potente de pruebas de seguridad que solo debe utilizarse en **pruebas autorizadas**.
> 
> - ✅ Probar aplicaciones propias
> - ✅ Realizar pruebas con autorización expresa por escrito
> - ✅ Realizar pruebas en entornos dedicados
> - ❌ No analizar sistemas de producción sin autorización
> - ❌ No usar para acceder sin autorización
> 
> **Las pruebas de seguridad sin autorización son ilegales y pueden dar lugar a cargos penales.**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Documentación de Burp Suite](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Recursos de aprendizaje
- [OWASP Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guías rápidas relacionadas
- [SQLMap](../../SQLMap/translations/README.es.md)
- [Metasploit Framework](../../Metasploit/translations/README.es.md)

---

<p align="center">
  <b>🔶 ¡Domina las pruebas de seguridad de aplicaciones web!</b><br>
  <i>La mejor forma de aprender es practicar: prepara un laboratorio y ponte a prueba.</i>
</p>
