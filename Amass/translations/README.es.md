# 🌐 Amass: cartografía de la superficie de ataque

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)


> **Terminología y versiones:** ASN es un número de sistema autónomo; CIDR expresa prefijos de red; wordlist es una lista de palabras. Se conservan las órdenes, la imagen Docker y el YAML del original, que mezclan interfaces de distintas versiones. En la [guía oficial histórica de Amass](https://github.com/owasp-amass/amass/wiki/User-Guide), `-r` especifica resolutores DNS: no activa la recursividad. `-active` no equivale por sí solo a `-brute`. Los subcomandos, la configuración, las fuentes y las modalidades gratuitas deben contrastarse con la versión y el proveedor. El último paso con `httpx` comprueba servicios HTTP, no se limita a resolver DNS. Los resultados descubiertos deben verificarse antes de atribuirlos a una organización.


```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         Cartografía detallada de la superficie de ataque
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Índice

- [Qué es Amass](#qué-es-amass)
- [Instalación](#instalación)
- [Modo de enumeración](#modo-de-enumeración)
- [Modo Intel](#modo-intel)
- [Modo DNS](#modo-dns)
- [Configuración](#configuración)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-amass"></a>

## 🎯 Qué es Amass

**Amass** es un proyecto de OWASP para cartografiar redes y descubrir activos externos:

- 🔍 **Enumeración de subdominios**: la herramienta más completa
- 🏢 **Descubrimiento de ASN**: encontrar los rangos de IP de una organización
- 📡 **Inteligencia DNS**: reconocimiento pasivo y activo
- 🗺️ **Cartografía de la superficie de ataque**: visualizar la infraestructura

### ¿Por qué Amass?

| Función | Ventaja |
|---------|---------|
| Más de 55 fuentes de datos | Los resultados más completos |
| Pasivo y activo | Elegir el nivel de actividad visible |
| Inteligencia de ASN y organizaciones | Encontrar infraestructura oculta |
| Base de datos de grafos | Visualizar relaciones |

---

<a id="instalación"></a>

## 🚀 Instalación

```bash
# Instalación con Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Verificar
amass -version
```

---

<a id="modo-de-enumeración"></a>

## 🔍 Modo de enumeración

### Enumeración básica de subdominios

```bash
# Enumeración pasiva (sin contacto directo con el objetivo)
amass enum -passive -d example.com

# Enumeración activa (fuerza bruta DNS, según la descripción del original)
amass enum -active -d example.com

# Fuerza bruta con una lista de palabras
amass enum -active -brute -d example.com -w wordlist.txt
```

### Opciones de salida

```bash
# Guardar en un archivo
amass enum -passive -d example.com -o subdomains.txt

# Salida JSON
amass enum -passive -d example.com -json output.json

# Guardar en un directorio
amass enum -passive -d example.com -dir ./amass_output/
```

### Varios objetivos

```bash
# Varios dominios
amass enum -passive -d example.com -d target.com

# Desde un archivo
amass enum -passive -df domains.txt -o results.txt
```

### Enumeración avanzada

```bash
# Establecer el tiempo de espera (minutos)
amass enum -passive -d example.com -timeout 30

# Limitar las fuentes
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Excluir fuentes
amass enum -passive -d example.com -exclude Google,Bing

# Mostrar las fuentes de datos
amass enum -passive -d example.com -src
```

### Enumeración recursiva

```bash
# Activar la búsqueda recursiva (descripción del original)
amass enum -passive -d example.com -r

# Establecer la profundidad de recursividad
amass enum -passive -d example.com -max-depth 3
```

---

<a id="modo-intel"></a>

## 🏢 Modo Intel

> El modo Intel descubre dominios raíz, ASN y rangos CIDR asociados a organizaciones.

### Encontrar los activos de una organización

```bash
# Por nombre de organización
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# WHOIS inverso
amass intel -whois -d example.com
```

### Descubrimiento de ASN

```bash
# Encontrar el ASN de un dominio
amass intel -d example.com

# Enumerar por ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Varios ASN
amass intel -asn 13335,15169
```

### Rango CIDR

```bash
# Encontrar activos en un rango CIDR
amass intel -active -cidr 192.168.1.0/24

# Varios rangos CIDR
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### Rango de IP

```bash
# Rango de direcciones IP
amass intel -active -ip 192.168.1.1-254
```

---

<a id="modo-dns"></a>

## 📡 Modo DNS

### Consultas DNS

```bash
# Consulta DNS básica
amass dns -d example.com

# Tipos de registro específicos
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Resolver subdominios

```bash
# Resolver los subdominios descubiertos
amass dns -df subdomains.txt
```

---

<a id="configuración"></a>

## ⚙️ Configuración

### Ubicación del archivo de configuración

```bash
# Ubicación predeterminada
~/.config/amass/config.yaml

# Especificar una configuración personalizada
amass enum -config ./my_config.yaml -d example.com
```

### Ejemplo de archivo de configuración

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Fuentes de datos con claves de API

| Fuente | Modalidad gratuita | API requerida |
|--------|-----------|--------------|
| Crt.sh | ✅ | No |
| SecurityTrails | ✅ | Sí |
| Shodan | ✅ | Sí |
| VirusTotal | ✅ | Sí |
| Censys | ✅ | Sí |
| PassiveTotal | Limitado | Sí |
| BinaryEdge | Limitado | Sí |

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos de enumeración

| Comando | Descripción |
|---------|-------------|
| `amass enum -passive -d domain.com` | Enumeración pasiva de subdominios |
| `amass enum -active -d domain.com` | Enumeración DNS activa |
| `amass enum -active -brute -d domain.com` | Enumerar subdominios por fuerza bruta |
| `amass enum -d domain.com -o out.txt` | Guardar en un archivo |
| `amass enum -d domain.com -json out.json` | Salida JSON |

### Comandos de Intel

| Comando | Descripción |
|---------|-------------|
| `amass intel -org "Company"` | Encontrar activos por nombre de organización |
| `amass intel -asn 12345` | Enumerar por ASN |
| `amass intel -cidr 10.0.0.0/8` | Enumerar un rango CIDR |
| `amass intel -whois -d domain.com` | WHOIS inverso |

### Opciones esenciales

| Opción | Descripción |
|------|-------------|
| `-d` | Dominio objetivo |
| `-df` | Dominios desde un archivo |
| `-o` | Archivo de salida |
| `-passive` | Solo pasivo (sin DNS) |
| `-active` | Activar DNS activo |
| `-brute` | Fuerza bruta DNS |
| `-src` | Mostrar la fuente de datos |
| `-timeout` | Tiempo de espera en minutos |

---

## 🎯 Flujo de trabajo para bug bounty

```bash
# 1. Enumeración pasiva (segura, según la descripción del original)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Enumeración activa con fuerza bruta
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Encontrar los activos de la organización
amass intel -org "Target Inc" -o org_domains.txt

# 4. Combinar los resultados
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Resolver los hosts activos (descripción del original)
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Recursos

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Guía del usuario de Amass](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Guías rápidas relacionadas
- [Metodología de bug bounty](../../Bug-Bounty-Methodology/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)
- [httpx](../../httpx/translations/README.es.md)

---

<p align="center">
  <b>🌐 ¡Cartografía la superficie de ataque!</b><br>
  <i>Amass: la herramienta de subdominios más completa</i>
</p>
