# 🚀 Gobuster: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Aclaraciones del original:** VHost significa host virtual y wordlist, lista de palabras. Las opciones cambian según la versión de [Gobuster](https://github.com/OJ/gobuster). `--no-status` oculta los códigos de estado; no hace que se incluyan todos los resultados. La selección con `-s` puede requerir desactivar la lista de exclusión predeterminada. El ejemplo `8.8.8.8:53` indica un solo resolutor y su puerto, aunque el comentario diga varios. Para nombres de VHost incompletos puede ser necesario `--append-domain`. La tabla conserva la comparación original, pero [ffuf sí admite recursividad](https://github.com/ffuf/ffuf/wiki/Recursion). Las estrellas son valoraciones orientativas.


```
   ██████╗  ██████╗ ██████╗ ██╗   ██╗███████╗████████╗███████╗██████╗ 
  ██╔════╝ ██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝██╔════╝██╔══██╗
  ██║  ███╗██║   ██║██████╔╝██║   ██║███████╗   ██║   █████╗  ██████╔╝
  ██║   ██║██║   ██║██╔══██╗██║   ██║╚════██║   ██║   ██╔══╝  ██╔══██╗
  ╚██████╔╝╚██████╔╝██████╔╝╚██████╔╝███████║   ██║   ███████╗██║  ██║
   ╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
                Herramienta de fuerza bruta de directorios, DNS y VHost
```

<p align="center">
  <img src="https://img.shields.io/badge/Gobuster-Enumeration-blue?style=for-the-badge" alt="Gobuster">
  <img src="https://img.shields.io/badge/Directory-Bruteforce-red?style=for-the-badge" alt="Directory">
  <img src="https://img.shields.io/badge/DNS-Subdomain-green?style=for-the-badge" alt="DNS">
  <img src="https://img.shields.io/badge/VHost-Discovery-purple?style=for-the-badge" alt="VHost">
</p>

<p align="center">
  <b>🔍 Herramienta rápida de enumeración de directorios, DNS y VHost escrita en Go</b>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Instalación](#instalación)
- [Resumen de modos](#resumen-de-modos)
- [Modo de directorios (dir)](#modo-de-directorios-dir)
- [Modo DNS (dns)](#modo-dns-dns)
- [Modo VHost (vhost)](#modo-vhost-vhost)
- [Modo fuzz (fuzz)](#modo-fuzz-fuzz)
- [Listas de palabras](#listas-de-palabras)
- [Autenticación y cabeceras](#autenticación-y-cabeceras)
- [Ajuste del rendimiento](#ajuste-del-rendimiento)
- [Ejemplos prácticos](#ejemplos-prácticos)
- [Referencia rápida](#referencia-rápida)
- [Consejos y buenas prácticas](#consejos-y-buenas-prácticas)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**Gobuster** es una herramienta rápida de fuerza bruta, escrita en Go y con varios hilos, para:
- Enumerar directorios y archivos en servidores web
- Descubrir subdominios DNS
- Descubrir hosts virtuales (VHost)
- Realizar fuzzing de URL (probar valores de forma automatizada)

### ¿Por qué Gobuster?

| Característica | Descripción |
|---------|-------------|
| **Rápido** | Escrito en Go, con gran capacidad de concurrencia |
| **Flexible** | Varios modos para distintas tareas |
| **Sin recursividad** | Resultados claros y sin ruido |
| **Estable** | Gestiona los errores de forma controlada |
| **Desarrollo activo** | Actualizaciones periódicas |

### Comparación con otras herramientas

| Herramienta | Velocidad | Recursividad | Lenguaje |
|------|-------|-----------|----------|
| **Gobuster** | ⭐⭐⭐⭐⭐ | No | Go |
| Dirb | ⭐⭐ | Sí | C |
| Dirbuster | ⭐⭐⭐ | Sí | Java |
| Ffuf | ⭐⭐⭐⭐⭐ | No | Go |
| Feroxbuster | ⭐⭐⭐⭐⭐ | Sí | Rust |

---

<a id="instalación"></a>

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
# Actualizar Gobuster
sudo apt update && sudo apt install gobuster
```

### Ubuntu/Debian
```bash
sudo apt install gobuster
```

### Desde el código fuente (Go)
```bash
go install github.com/OJ/gobuster/v3@latest
```

### Mediante Snap
```bash
sudo snap install gobuster
```

### Desde las versiones publicadas en GitHub
```bash
# Descargar la última versión
wget https://github.com/OJ/gobuster/releases/latest/download/gobuster-linux-amd64.7z
7z x gobuster-linux-amd64.7z
chmod +x gobuster
sudo mv gobuster /usr/local/bin/
```

### Verificar la instalación
```bash
gobuster version
gobuster -h
```

---

<a id="resumen-de-modos"></a>

## 📊 Resumen de modos

### Modos disponibles

| Modo | Descripción | Uso |
|------|-------------|----------|
| `dir` | Enumeración de directorios y archivos | Encontrar directorios y archivos ocultos |
| `dns` | Enumeración de subdominios DNS | Encontrar subdominios |
| `vhost` | Enumeración de hosts virtuales | Encontrar hosts virtuales |
| `fuzz` | Realizar fuzzing de URL (probar valores de forma automatizada) | Probar parámetros y rutas mediante fuzzing |
| `s3` | Enumeración de buckets S3 | Encontrar buckets de AWS S3 |
| `gcs` | Google Cloud Storage | Encontrar buckets de GCS |
| `tftp` | Enumeración de TFTP | Encontrar archivos TFTP |

### Sintaxis básica
```bash
gobuster [mode] [options]

# Ejemplos
gobuster dir -u http://target.com -w wordlist.txt
gobuster dns -d target.com -w subdomains.txt
gobuster vhost -u http://target.com -w vhosts.txt
```

---

<a id="modo-de-directorios-dir"></a>

## 📁 Modo de directorios (dir)

### Sintaxis básica
```bash
gobuster dir -u <URL> -w <wordlist>
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-u` | URL del objetivo |
| `-w` | Ruta de la lista de palabras |
| `-t` | Hilos (valor predeterminado: 10) |
| `-x` | Extensiones de archivo que se deben buscar |
| `-s` | Códigos de estado que se aceptan |
| `-b` | Códigos de estado excluidos (lista de bloqueo) |
| `-o` | Archivo de salida |
| `-k` | Omitir la verificación SSL |
| `-r` | Seguir las redirecciones |
| `-a` | Cadena User-Agent |
| `-c` | Cookies |
| `-H` | Cabeceras personalizadas |

### Enumeración básica de directorios
```bash
# Enumeración sencilla de directorios
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt

# Con más hilos
gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt -t 50
```

### Búsqueda de extensiones de archivo
```bash
# Buscar extensiones específicas
gobuster dir -u http://target.com -w wordlist.txt -x php,html,txt

# Varias extensiones
gobuster dir -u http://target.com -w wordlist.txt -x php,asp,aspx,jsp,html,txt,bak,old

# Archivos de copia de seguridad
gobuster dir -u http://target.com -w wordlist.txt -x bak,backup,old,orig,~
```

### Filtrado por códigos de estado
```bash
# Mostrar solo códigos de estado específicos
gobuster dir -u http://target.com -w wordlist.txt -s 200,204,301,302,307,401

# Excluir códigos de estado
gobuster dir -u http://target.com -w wordlist.txt -b 404,403

# Mostrar todo (incluido 404, según el comentario original)
gobuster dir -u http://target.com -w wordlist.txt --no-status
```

### Opciones de salida
```bash
# Guardar en un archivo
gobuster dir -u http://target.com -w wordlist.txt -o results.txt

# Salida detallada
gobuster dir -u http://target.com -w wordlist.txt -v

# Sin progreso (descripción del original)
gobuster dir -u http://target.com -w wordlist.txt -q

# Modo expandido (URL completas)
gobuster dir -u http://target.com -w wordlist.txt -e
```

### Opciones de SSL/TLS
```bash
# Omitir la verificación SSL
gobuster dir -u https://target.com -w wordlist.txt -k

# Omitir SSL con opciones adicionales
gobuster dir -u https://target.com -w wordlist.txt -k --no-tls-validation
```

### Ejemplos del modo de directorios
```bash
# Enumeración habitual de directorios web
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt -t 50

# Aplicación PHP
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x php -t 50

# Descubrimiento de endpoints de API
gobuster dir -u http://target.com/api -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt -t 30

# Buscar archivos de copia de seguridad
gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/common.txt -x bak,backup,old,zip,tar.gz -t 50
```

---

<a id="modo-dns-dns"></a>

## 🌐 Modo DNS (dns)

### Sintaxis básica
```bash
gobuster dns -d <domain> -w <wordlist>
```

### Opciones de DNS

| Opción | Descripción |
|--------|-------------|
| `-d` | Dominio objetivo |
| `-w` | Ruta de la lista de palabras |
| `-t` | Hilos |
| `-r` | Resolutor DNS personalizado |
| `-i` | Mostrar direcciones IP |
| `-c` | Mostrar registros CNAME |
| `--wildcard` | Forzar el procesamiento de resultados wildcard (comodín) |
| `--timeout` | Tiempo de espera DNS |

### Enumeración DNS básica
```bash
# Enumeración sencilla de subdominios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Mostrar las IP
gobuster dns -d target.com -w subdomains.txt -i

# Mostrar los CNAME
gobuster dns -d target.com -w subdomains.txt -c
```

### Resolutor DNS personalizado
```bash
# Usar un servidor DNS específico
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8

# Varios resolutores (descripción del original)
gobuster dns -d target.com -w subdomains.txt -r 8.8.8.8:53
```

### Ejemplos de DNS
```bash
# Enumeración exhaustiva de subdominios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt -t 50 -i

# Enumeración rápida de subdominios
gobuster dns -d target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt -t 100

# Con archivo de salida
gobuster dns -d target.com -w subdomains.txt -i -o subdomains_found.txt
```

---

<a id="modo-vhost-vhost"></a>

## 🏠 Modo VHost (vhost)

### Sintaxis básica
```bash
gobuster vhost -u <URL> -w <wordlist>
```

### Opciones de VHost

| Opción | Descripción |
|--------|-------------|
| `-u` | URL del objetivo |
| `-w` | Ruta de la lista de palabras |
| `-t` | Hilos |
| `--append-domain` | Añadir el dominio a las entradas de la lista |
| `--domain` | Dominio para el host virtual |
| `-r` | Seguir las redirecciones |

### Enumeración básica de VHost
```bash
# Enumeración sencilla de VHost
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt

# Añadiendo el dominio
gobuster vhost -u http://target.com -w subdomains.txt --append-domain
```

### Ejemplos de VHost
```bash
# Descubrimiento habitual de VHost
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/namelist.txt -t 50

# Hosts de desarrollo
gobuster vhost -u http://target.com -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt --append-domain

# Con un dominio personalizado
gobuster vhost -u http://10.10.10.10 -w subdomains.txt --domain target.htb
```

---

<a id="modo-fuzz-fuzz"></a>

## 🔧 Modo fuzz (fuzz)

### Sintaxis básica
```bash
gobuster fuzz -u <URL with FUZZ> -w <wordlist>
```

### Opciones de fuzz

| Opción | Descripción |
|--------|-------------|
| `-u` | URL con la palabra clave FUZZ |
| `-w` | Ruta de la lista de palabras |
| `-t` | Hilos |
| `-b` | Excluir códigos de estado |
| `--exclude-length` | Excluir por longitud de respuesta |

### Ejemplos de fuzz
```bash
# Probar el valor de un parámetro mediante fuzzing
gobuster fuzz -u "http://target.com/page?id=FUZZ" -w numbers.txt

# Probar una ruta mediante fuzzing
gobuster fuzz -u "http://target.com/FUZZ/admin" -w wordlist.txt

# Excluir longitudes específicas
gobuster fuzz -u "http://target.com/FUZZ" -w wordlist.txt --exclude-length 0,42
```

---

<a id="listas-de-palabras"></a>

## 📚 Listas de palabras

### Ubicaciones de SecLists (Kali Linux)

```bash
# Enumeración de directorios y archivos
/usr/share/seclists/Discovery/Web-Content/

# Listas habituales de directorios
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-small.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# DNS y subdominios
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

### Listas de palabras predeterminadas

```bash
# Listas de palabras de Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
/usr/share/wordlists/dirb/small.txt

# Listas de palabras de Dirbuster
/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt
/usr/share/wordlists/dirbuster/directory-list-2.3-small.txt
```

### Selección de listas de palabras

| Uso | Lista de palabras |
|----------|----------|
| Enumeración rápida | `common.txt` |
| Habitual | `directory-list-2.3-medium.txt` |
| Exhaustiva | `directory-list-2.3-big.txt` |
| API | `api-endpoints.txt` |
| Subdominios | `subdomains-top1million-5000.txt` |
| Archivos | `raft-large-files.txt` |

---

<a id="autenticación-y-cabeceras"></a>

## 🔐 Autenticación y cabeceras

### Autenticación básica
```bash
# Autenticación HTTP Basic
gobuster dir -u http://target.com -w wordlist.txt -U username -P password
```

### Autenticación mediante cookies
```bash
# Una cookie
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123"

# Varias cookies
gobuster dir -u http://target.com -w wordlist.txt -c "session=abc123; token=xyz789"

# Sesión PHP
gobuster dir -u http://target.com -w wordlist.txt -c "PHPSESSID=abc123def456"
```

### Cabeceras personalizadas
```bash
# Una cabecera
gobuster dir -u http://target.com -w wordlist.txt -H "Authorization: Bearer token123"

# Varias cabeceras
gobuster dir -u http://target.com -w wordlist.txt -H "X-Forwarded-For: 127.0.0.1" -H "X-Custom: value"

# Clave de API
gobuster dir -u http://target.com/api -w wordlist.txt -H "X-API-Key: your-api-key"
```

### User-Agent
```bash
# User-Agent personalizado
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"

# Googlebot
gobuster dir -u http://target.com -w wordlist.txt -a "Googlebot/2.1"
```

### Uso de proxies
```bash
# Proxy HTTP
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080

# Proxy SOCKS5
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050

# Proxy de Burp Suite
gobuster dir -u http://target.com -w wordlist.txt --proxy http://127.0.0.1:8080 -k
```

---

<a id="ajuste-del-rendimiento"></a>

## ⚡ Ajuste del rendimiento

### Gestión de hilos
```bash
# Valor predeterminado: 10 hilos
gobuster dir -u http://target.com -w wordlist.txt

# Aumentar los hilos (red rápida)
gobuster dir -u http://target.com -w wordlist.txt -t 100

# Reducir los hilos (red lenta o inestable)
gobuster dir -u http://target.com -w wordlist.txt -t 5
```

### Configuración del tiempo de espera
```bash
# Aumentar el tiempo de espera
gobuster dir -u http://target.com -w wordlist.txt --timeout 30s

# Tiempo de espera DNS
gobuster dns -d target.com -w subdomains.txt --timeout 5s
```

### Intervalo entre solicitudes
```bash
# Añadir un intervalo (limitación de la tasa de solicitudes)
gobuster dir -u http://target.com -w wordlist.txt --delay 100ms

# Más lento para evadir el WAF
gobuster dir -u http://target.com -w wordlist.txt --delay 500ms
```

---

<a id="ejemplos-prácticos"></a>

## 🎬 Ejemplos prácticos

### Ejemplo 1: enumeración web habitual
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt \
    -t 50 \
    -x php,html,txt \
    -o results.txt
```

### Ejemplo 2: descubrimiento de endpoints de API
```bash
gobuster dir -u http://target.com/api/v1 \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -t 30 \
    -H "Content-Type: application/json"
```

### Ejemplo 3: enumeración autenticada
```bash
gobuster dir -u http://target.com/admin \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -c "PHPSESSID=abc123" \
    -t 20
```

### Ejemplo 4: enumeración de subdominios
```bash
gobuster dns -d target.com \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-110000.txt \
    -t 100 \
    -i \
    -o subdomains.txt
```

### Ejemplo 5: descubrimiento de VHost (HackTheBox)
```bash
gobuster vhost -u http://10.10.10.10 \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    --domain target.htb \
    --append-domain
```

### Ejemplo 6: mediante el proxy de Burp
```bash
gobuster dir -u https://target.com \
    -w /usr/share/wordlists/dirb/common.txt \
    --proxy http://127.0.0.1:8080 \
    -k \
    -t 20
```

### Ejemplo 7: búsqueda de copias de seguridad
```bash
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/common.txt \
    -x bak,backup,old,orig,save,swp,txt,zip,tar,gz,sql \
    -t 50
```

### Ejemplo 8: detección de CMS
```bash
# WordPress
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/wordpress.fuzz.txt \
    -t 50

# Joomla
gobuster dir -u http://target.com \
    -w /usr/share/seclists/Discovery/Web-Content/CMS/joomla-plugins.fuzz.txt
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Enumeración básica de directorios | `gobuster dir -u URL -w wordlist.txt` |
| Con extensiones | `gobuster dir -u URL -w wordlist.txt -x php,html` |
| Enumeración de subdominios | `gobuster dns -d domain.com -w wordlist.txt` |
| Enumeración de VHost | `gobuster vhost -u URL -w wordlist.txt` |
| Con archivo de salida | `gobuster dir -u URL -w wordlist.txt -o output.txt` |
| Omitir errores SSL | `gobuster dir -u URL -w wordlist.txt -k` |
| Con cookies | `gobuster dir -u URL -w wordlist.txt -c "session=x"` |
| Mediante un proxy | `gobuster dir -u URL -w wordlist.txt --proxy http://127.0.0.1:8080` |

### Opciones habituales

| Opción | Abreviatura | Descripción |
|--------|-------|-------------|
| `--url` | `-u` | URL del objetivo |
| `--wordlist` | `-w` | Archivo de lista de palabras |
| `--threads` | `-t` | Número de hilos |
| `--extensions` | `-x` | Extensiones de archivo |
| `--status-codes` | `-s` | Códigos de estado que se aceptan |
| `--cookies` | `-c` | Cookies que se enviarán |
| `--output` | `-o` | Archivo de salida |
| `--insecuressl` | `-k` | Omitir la verificación SSL |
| `--verbose` | `-v` | Salida detallada |
| `--quiet` | `-q` | Modo silencioso |

---

<a id="consejos-y-buenas-prácticas"></a>

## 💡 Consejos y buenas prácticas

### Consejos de eficiencia

1. **Empezar con poco y ampliar después**
   ```bash
   # Primero, una enumeración rápida
   gobuster dir -u http://target.com -w /usr/share/wordlists/dirb/common.txt
   
   # Después, una lista de palabras más grande
   gobuster dir -u http://target.com -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
   ```

2. **Extensiones específicas del objetivo**
   ```bash
   # Sitio PHP
   gobuster dir -u http://target.com -w wordlist.txt -x php
   
   # Sitio ASP.NET
   gobuster dir -u http://target.com -w wordlist.txt -x asp,aspx
   ```

3. **Combinar con otras herramientas**
   ```bash
   # Flujo de trabajo Nmap → Gobuster
   nmap -sV -p 80,443 target.com
   gobuster dir -u http://target.com -w wordlist.txt
   ```

### Evitar la detección

```bash
# Enumeración lenta con un intervalo entre solicitudes
gobuster dir -u http://target.com -w wordlist.txt -t 5 --delay 200ms

# User-Agent personalizado
gobuster dir -u http://target.com -w wordlist.txt -a "Mozilla/5.0..."

# Mediante Tor
gobuster dir -u http://target.com -w wordlist.txt --proxy socks5://127.0.0.1:9050
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Gobuster es una herramienta potente de enumeración que solo debe utilizarse en **pruebas autorizadas**.
> 
> - ✅ Realizar pruebas en sistemas propios
> - ✅ Realizar pruebas con autorización expresa por escrito
> - ✅ Usar en retos CTF y laboratorios
> - ❌ No realizar exploraciones sin autorización
> - ❌ La enumeración sin autorización puede ser ilegal
> 
> **Obtener siempre permiso antes de explorar cualquier sistema.**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [Gobuster GitHub](https://github.com/OJ/gobuster)
- [Gobuster Wiki](https://github.com/OJ/gobuster/wiki)

### Listas de palabras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [FuzzDB](https://github.com/fuzzdb-project/fuzzdb)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)

### Guías rápidas relacionadas
- [Nmap](../../Nmap/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)
- [SQLMap](../../SQLMap/translations/README.es.md)

---

<p align="center">
  <b>🚀 ¡Domina la enumeración web!</b><br>
  <i>Descubre lo que otros no ven</i>
</p>
