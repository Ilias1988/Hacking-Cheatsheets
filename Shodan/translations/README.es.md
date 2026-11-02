# 🌐 Shodan: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)


> **Precisiones del original:** Se conservan todas las consultas, precios y valores, incluidos términos ingleses de búsqueda. Los planes y prestaciones son datos del original y pueden cambiar. Los banners (registros de servicios observados) son observaciones indexadas; una versión, un título o un resultado de CVE no demuestra por sí solo una vulnerabilidad o ausencia de autenticación. Shodan distingue vulnerabilidades verificadas y no verificadas ([documentación oficial](https://help.shodan.io/mastery/vulnerability-assessment)). La consulta de certificado autofirmado del ejemplo busca un valor literal; no compara automáticamente dos campos. `2024` es una fecha histórica, no significa «próximamente». Los rangos privados son ejemplos de sintaxis, no objetivos de la Internet pública. Deben contrastarse los filtros, el uso de `||`, los paréntesis y `vuln:` sin valor con la [sintaxis oficial](https://help.shodan.io/the-basics/search-query-fundamentals). Un certificado o ASN tampoco prueba por sí solo la pertenencia de un activo al alcance autorizado.


```
  ███████╗██╗  ██╗ ██████╗ ██████╗  █████╗ ███╗   ██╗
  ██╔════╝██║  ██║██╔═══██╗██╔══██╗██╔══██╗████╗  ██║
  ███████╗███████║██║   ██║██║  ██║███████║██╔██╗ ██║
  ╚════██║██╔══██║██║   ██║██║  ██║██╔══██║██║╚██╗██║
  ███████║██║  ██║╚██████╔╝██████╔╝██║  ██║██║ ╚████║
  ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝
                                                      
   El buscador de dispositivos conectados a Internet
```

<p align="center">
  <img src="https://img.shields.io/badge/Shodan-IoT_Search-red?style=for-the-badge" alt="Shodan">
  <img src="https://img.shields.io/badge/OSINT-orange?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Reconnaissance-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/IoT-Security-green?style=for-the-badge" alt="IoT">
</p>

<p align="center">
  <b>🔍 Buscador de dispositivos, servidores e infraestructura conectados a Internet</b><br>
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Primeros pasos](#primeros-pasos)
- [Filtros básicos](#filtros-básicos)
- [Filtros de red](#filtros-de-red)
- [Filtros SSL/TLS](#filtros-ssltls)
- [Filtros HTTP](#filtros-http)
- [Servicios vulnerables](#servicios-vulnerables)
- [IoT e ICS/SCADA](#iot-e-icsscada)
- [Bases de datos](#bases-de-datos)
- [Cámaras web y multimedia](#cámaras-web-y-multimedia)
- [Servicios en la nube](#servicios-en-la-nube)
- [Consultas para bug bounty](#consultas-para-bug-bounty)
- [Shodan CLI](#shodan-cli)
- [Referencia rápida](#referencia-rápida)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**Shodan** es un buscador que permite encontrar determinados tipos de equipos, servidores y dispositivos IoT conectados a Internet mediante distintos filtros.

### ¿Qué puedes encontrar?

| Categoría | Ejemplos |
|----------|----------|
| **Servidores** | Servidores web, FTP, SSH y bases de datos |
| **Dispositivos IoT** | Cámaras, routers y dispositivos inteligentes |
| **ICS/SCADA** | Sistemas de control industrial |
| **Nube** | Instancias de AWS, Azure y GCP |
| **Vulnerabilidades** | Servicios expuestos y credenciales predeterminadas |

### URL de Shodan

| Servicio | URL |
|---------|-----|
| **Búsqueda web** | https://www.shodan.io |
| **Mapas** | https://maps.shodan.io |
| **Exploits** | https://exploits.shodan.io |
| **Imágenes** | https://images.shodan.io |

---

<a id="primeros-pasos"></a>

## 🚀 Primeros pasos

### Crear una cuenta

1. Ir a https://www.shodan.io
2. Crear una cuenta gratuita
3. Obtener la clave de API en la configuración de la cuenta

### Planes de suscripción

| Plan | Precio | Funciones |
|------|-------|----------|
| **Gratuito** | $0 | Búsqueda básica y resultados limitados |
| **Membership** | $49 | Más resultados, descargas y CLI |
| **Small Business** | $299/mo | Más créditos y supervisión |
| **Corporate** | $899/mo | Acceso ilimitado |

### Clave de API

```bash
# Obtener de: https://account.shodan.io
# Exportar para la CLI
export SHODAN_API_KEY="YOUR_API_KEY"
```

---

<a id="filtros-básicos"></a>

## 🔧 Filtros básicos

### Búsqueda de texto

```
# Búsqueda sencilla
apache
nginx
Microsoft-IIS

# Búsqueda de productos
product:apache
product:nginx
product:openssh
```

### País y ciudad

```
# País (ISO 3166-1 alfa-2)
country:US
country:DE
country:RU
country:CN
country:GR                      # Grecia

# Ciudad
city:Athens
city:"New York"
city:London
city:Tokyo

# Combinar
country:US city:Miami
country:GR city:Thessaloniki
```

### Organización y proveedor de Internet (ISP)

```
# Organización
org:"Google"
org:"Amazon"
org:"Microsoft"
org:"Facebook"

# ISP
isp:"Comcast"
isp:"Verizon"
isp:"AT&T"
```

### Nombre de host y dominio

```
# El nombre de host contiene
hostname:google.com
hostname:.gov
hostname:.edu
hostname:target.com

# Dominio específico
ssl.cert.subject.cn:target.com
```

### Sistema operativo

```
os:"Windows"
os:"Linux"
os:"Ubuntu"
os:"Windows Server 2019"
os:"Windows 7"
os:"FreeBSD"
```

---

<a id="filtros-de-red"></a>

## 🌐 Filtros de red

### Puerto

```
# Puerto específico
port:22                         # SSH
port:21                         # FTP
port:23                         # Telnet
port:80                         # HTTP
port:443                        # HTTPS
port:3389                       # RDP
port:3306                       # MySQL
port:5432                       # PostgreSQL
port:27017                      # MongoDB
port:6379                       # Redis
port:9200                       # Elasticsearch

# Varios puertos
port:22,80,443
```

### IP y red

```
# IP específica
ip:8.8.8.8

# Rango de IP (CIDR)
net:192.168.0.0/24
net:10.0.0.0/8
net:172.16.0.0/12

# ASN
asn:AS15169                     # Google
asn:AS16509                     # Amazon
asn:AS8075                      # Microsoft
```

### Geolocalización

```
# Coordenadas geográficas
geo:37.9838,23.7275             # Coordenadas de Atenas
geo:40.7128,-74.0060,10         # Nueva York con radio

# Región o estado
region:California
region:"New York"
```

---

<a id="filtros-ssltls"></a>

## 🔐 Filtros SSL/TLS

### Información del certificado

```
# Sujeto del certificado
ssl.cert.subject.cn:target.com
ssl.cert.subject.cn:*.target.com

# Emisor del certificado
ssl.cert.issuer.cn:"Let's Encrypt"
ssl.cert.issuer.cn:"DigiCert"

# Certificados autofirmados
ssl.cert.issuer.cn:ssl.cert.subject.cn
```

### Versiones de SSL

```
# Versiones de SSL (vulnerables)
ssl.version:sslv2
ssl.version:sslv3
ssl.version:tlsv1
ssl.version:tlsv1.1

# Versiones seguras
ssl.version:tlsv1.2
ssl.version:tlsv1.3
```

### Caducidad del certificado

```
# Certificados caducados
ssl.cert.expired:true

# Certificados que caducan pronto
ssl.cert.expires:2024
```

### Vulnerabilidades SSL

```
# Heartbleed
vuln:CVE-2014-0160

# POODLE
ssl.version:sslv3

# DROWN
ssl.version:sslv2
```

---

<a id="filtros-http"></a>

## 🌍 Filtros HTTP

### Estado HTTP y cabeceras

```
# Código de estado HTTP
http.status:200
http.status:401
http.status:403
http.status:500

# Título HTTP
http.title:"Dashboard"
http.title:"Login"
http.title:"Admin"
http.title:"Index of"

# Contenido HTML
http.html:"password"
http.html:"admin"
http.html:"login"
```

### Tecnologías web

```
# Cabecera del servidor
http.server:apache
http.server:nginx
http.server:Microsoft-IIS

# Versiones específicas
http.server:"Apache/2.4.49"     # Vulnerable
http.server:"nginx/1.18"

# Hash del favicon
http.favicon.hash:116323821     # Favicon específico
http.favicon.hash:-1137975059   # Jenkins
```

### Componentes

```
# Frameworks web
http.component:"WordPress"
http.component:"Joomla"
http.component:"Drupal"
http.component:"Laravel"
http.component:"Django"

# Tecnologías
http.component:"PHP"
http.component:"jQuery"
http.component:"Bootstrap"
```

---

<a id="servicios-vulnerables"></a>

## ⚠️ Servicios vulnerables

### CVE conocidas

```
# Buscar by CVE
vuln:CVE-2021-44228            # Log4Shell
vuln:CVE-2021-26855            # ProxyLogon
vuln:CVE-2019-0708             # BlueKeep
vuln:CVE-2017-0144             # EternalBlue
vuln:CVE-2014-0160             # Heartbleed
vuln:CVE-2014-6271             # Shellshock

# Varias CVE
vuln:CVE-2021-44228,CVE-2021-45046
```

### Credenciales predeterminadas

```
# Contraseñas predeterminadas
"default password"
"admin" "password"
http.title:"Login" "admin"

# Valores predeterminados específicos
"admin:admin"
"root:root"
"admin:123456"
```

### Servicios abiertos o expuestos

```
# FTP anónimo
port:21 "Anonymous access granted"

# Telnet abierto
port:23 -authentication

# MongoDB abierto
port:27017 "MongoDB Server Information"

# Redis abierto
port:6379 -authentication

# Elasticsearch abierto
port:9200 "cluster_name"

# Memcached abierto
port:11211 "STAT"
```

### Paneles expuestos

```
# phpMyAdmin
http.title:"phpMyAdmin"

# cPanel
http.title:"cPanel"

# Webmin
http.title:"Webmin"

# Jenkins (sin autenticación)
http.title:"Dashboard [Jenkins]"

# Grafana
http.title:"Grafana"
```

---

<a id="iot-e-icsscada"></a>

## 🏭 IoT e ICS/SCADA

### Sistemas de control industrial

```
# ICS en general
tag:ics

# Modbus
port:502

# Siemens S7
port:102

# DNP3
port:20000

# BACnet
port:47808

# EtherNet/IP
port:44818

# Niagara Fox
port:1911
```

### Sistemas SCADA

```
# SCADA
tag:scada

# SCADA específico
"SCADA" port:502
"PLC" country:US
"Siemens" port:102
"Schneider"
```

### Dispositivos IoT

```
# IoT en general
tag:iot

# Routers
"router" port:80
http.title:"Router"

# Impresoras
port:9100
"printer"
http.title:"HP"

# Hogar inteligente
"smart home"
"IoT Gateway"
```

### Dispositivos de red

```
# Cisco
"cisco" product:cisco

# MikroTik
product:mikrotik
http.title:"RouterOS"

# Ubiquiti
product:ubiquiti

# Fortinet
product:fortigate
```

---

<a id="bases-de-datos"></a>

## 💾 Bases de datos

### MySQL

```
port:3306
port:3306 "MySQL"
port:3306 "unauthorized"
product:mysql
```

### PostgreSQL

```
port:5432
port:5432 "PostgreSQL"
product:postgresql
```

### MongoDB

```
port:27017
port:27017 "MongoDB Server Information"
port:27017 "totalSize"
product:mongodb
"MongoDB Server Information" -authentication
```

### Redis

```
port:6379
port:6379 "redis"
port:6379 -authentication
product:redis
```

### Elasticsearch

```
port:9200
port:9200 "elastic"
port:9200 "cluster_name"
port:9200 "You Know, for Search"
product:elasticsearch
```

### CouchDB

```
port:5984
port:5984 "couchdb"
"couchdb" "Welcome"
```

### Memcached

```
port:11211
port:11211 "STAT"
product:memcached
```

### Cassandra

```
port:9042
port:9160
product:cassandra
```

---

<a id="cámaras-web-y-multimedia"></a>

## 📹 Cámaras web y multimedia

### Cámaras IP

```
# Cámaras web genéricas
webcam
"webcam" country:US

# Marcas de cámaras
"hikvision"
"dahua"
"axis"
"foscam"
"netcam"

# Flujos RTSP
port:554 has_screenshot:true
"rtsp" port:554
```

### Sistemas DVR/NVR

```
"dvr" port:80
"nvr" 
http.title:"DVR"
http.title:"NVR"
"Network Video Recorder"
```

### Emisión multimedia

```
# Emisión con VLC
"VLC media player"

# Servidores multimedia
product:plex
"Plex Media Server"
```

### Interfaces de cámaras específicas

```
# Hikvision
http.title:"DVRDVS-Webs"
"App-webs" port:80

# Dahua
http.title:"WEB SERVICE"

# Axis
http.title:"AXIS"
```

---

<a id="servicios-en-la-nube"></a>

## ☁️ Servicios en la nube

### Amazon AWS

```
# AWS en general
org:"Amazon"
asn:AS16509

# Buckets S3
"AmazonS3"

# EC2
"ec2" org:"Amazon"

# Servicios de AWS
http.title:"AWS"
```

### Microsoft Azure

```
org:"Microsoft"
asn:AS8075
hostname:azure.com
hostname:cloudapp.azure.com
```

### Google Cloud

```
org:"Google"
asn:AS15169
hostname:googleusercontent.com
```

### DigitalOcean

```
org:"DigitalOcean"
asn:AS14061
```

### Paneles de nube expuestos

```
# Kubernetes Dashboard
http.title:"Kubernetes Dashboard"

# Docker
product:docker
port:2375

# Jenkins
http.title:"Jenkins"

# GitLab
http.title:"GitLab"
```

---

<a id="consultas-para-bug-bounty"></a>

## 🐛 Consultas para bug bounty

### Reconocimiento de organizaciones

```
# Organización objetivo
org:"Target Company"
ssl.cert.subject.cn:target.com
hostname:target.com

# Consulta de ASN
asn:AS12345
net:1.2.3.0/24
```

### Encontrar servicios expuestos

```
# Todos los servicios del objetivo
org:"Target" port:22,80,443,3306,5432,27017

# Paneles de administración
org:"Target" http.title:"admin"

# Páginas de inicio de sesión
org:"Target" http.title:"login"
```

### Descubrimiento de subdominios

```
ssl.cert.subject.cn:*.target.com
ssl.cert.subject.cn:target.com
hostname:target.com
```

### Servicios vulnerables

```
# Comprobar CVE
org:"Target" vuln:CVE-2021-44228

# Credenciales predeterminadas
org:"Target" "default password"

# Bases de datos abiertas
org:"Target" port:27017,6379,9200
```

### Consulta completa para bug bounty

```
# Reconocimiento completo
ssl.cert.subject.cn:target.com || hostname:target.com || org:"Target Company"

# Con filtros
(ssl.cert.subject.cn:target.com || hostname:target.com) port:22,80,443,3306,5432
```

---

<a id="shodan-cli"></a>

## 💻 Shodan CLI

### Instalación

```bash
# Instalar mediante pip
pip install shodan

# Inicializar con la clave de API
shodan init YOUR_API_KEY
```

### Comandos básicos

```bash
# Información de la cuenta
shodan info

# Buscar
shodan search "apache"

# Buscar con filtros
shodan search "port:22 country:US"

# Contar los resultados
shodan count "nginx"

# Descargar los resultados
shodan download results.json.gz "apache"

# Procesar los resultados
shodan parse results.json.gz

# Obtener información del host
shodan host 8.8.8.8
```

### CLI avanzada

```bash
# Información del dominio
shodan domain target.com

# Exploración (requiere créditos)
shodan scan submit 1.2.3.4

# Honeyscore (detección de honeypots)
shodan honeyscore 1.2.3.4

# Información de la IP propia
shodan myip

# Estadísticas
shodan stats "apache" --facets country,org
```

### Uso de la API (Python)

```python
import shodan

api = shodan.Shodan('YOUR_API_KEY')

# Buscar
results = api.search('apache')
for result in results['matches']:
    print(result['ip_str'], result['port'])

# Consulta de un host
host = api.host('8.8.8.8')
print(host['ip_str'], host['ports'])
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Filtros esenciales

| Filtro | Descripción | Ejemplo |
|--------|-------------|---------|
| `port:` | Número de puerto | `port:22` |
| `country:` | Código de país | `country:US` |
| `city:` | Nombre de ciudad | `city:London` |
| `org:` | Organización | `org:"Google"` |
| `hostname:` | Nombre de host | `hostname:google.com` |
| `net:` | Rango de IP o CIDR | `net:10.0.0.0/8` |
| `os:` | Sistema operativo | `os:"Windows"` |
| `product:` | Nombre del producto | `product:nginx` |
| `version:` | Versión | `version:"2.4.49"` |
| `vuln:` | Identificador de CVE | `vuln:CVE-2021-44228` |

### Filtros HTTP

| Filtro | Descripción | Ejemplo |
|--------|-------------|---------|
| `http.title:` | Título de la página | `http.title:"Admin"` |
| `http.status:` | Estado HTTP | `http.status:200` |
| `http.server:` | Cabecera del servidor | `http.server:nginx` |
| `http.html:` | Contenido HTML | `http.html:"login"` |
| `http.component:` | Tecnología | `http.component:"WordPress"` |

### Filtros SSL

| Filtro | Descripción | Ejemplo |
|--------|-------------|---------|
| `ssl.cert.subject.cn:` | Nombre común (CN) del certificado | `ssl.cert.subject.cn:target.com` |
| `ssl.cert.issuer.cn:` | Nombre común (CN) del emisor | `ssl.cert.issuer.cn:"DigiCert"` |
| `ssl.version:` | Versión de SSL | `ssl.version:sslv3` |
| `ssl.cert.expired:` | Certificado caducado | `ssl.cert.expired:true` |

### Diez consultas de Shodan destacadas

| # | Consulta | Finalidad |
|---|-------|---------|
| 1 | `port:22 country:US` | SSH en Estados Unidos |
| 2 | `port:3389 country:US` | RDP en Estados Unidos |
| 3 | `port:27017 -authentication` | MongoDB abierto |
| 4 | `vuln:CVE-2021-44228` | Log4Shell |
| 5 | `http.title:"Dashboard"` | Paneles de control |
| 6 | `"default password"` | Credenciales predeterminadas |
| 7 | `port:9200 "elastic"` | Elasticsearch |
| 8 | `webcam` | Cámaras IP |
| 9 | `product:apache vuln:` | Apache vulnerable |
| 10 | `ssl.cert.expired:true` | Certificados caducados |

---

## 💡 Consejos y buenas prácticas

### Consejos para bug bounty

1. **Empezar por la búsqueda de certificados**
   ```
   ssl.cert.subject.cn:target.com
   ```

2. **Comprobar todos los puertos**
   ```
   hostname:target.com port:22,80,443,3306,5432
   ```

3. **Buscar servicios expuestos**
   ```
   org:"Target" (port:27017 || port:9200 || port:6379)
   ```

4. **Comprobar vulnerabilidades**
   ```
   org:"Target" vuln:
   ```

### Combinar filtros

```
# Consultas complejas
port:22 country:US org:"Target" os:"Ubuntu"
http.title:"admin" -http.title:"Login" country:US
ssl.cert.subject.cn:target.com port:443 http.status:200
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Shodan muestra información accesible públicamente. Sin embargo:
> 
> - ✅ Usar para reconocimiento autorizado
> - ✅ Usar con objetivos de bug bounty incluidos en el alcance
> - ✅ Informar de vulnerabilidades de forma responsable
> - ❌ No explotar las vulnerabilidades encontradas
> - ❌ No acceder a sistemas sin permiso
> - ❌ Respetar la privacidad y las leyes
> 
> **¡Las exploraciones sin autorización son ilegales!**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [Shodan](https://www.shodan.io)
- [Ayuda de Shodan](https://help.shodan.io)
- [Shodan CLI](https://cli.shodan.io)
- [Shodan API](https://developer.shodan.io)

### Guías rápidas relacionadas
- [Google Dorking](../../Google-Dorking/translations/README.es.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)

---

<p align="center">
  <b>🌐 ¡Descubre la Internet oculta!</b><br>
  <i>Domina Shodan para el reconocimiento</i>
</p>
