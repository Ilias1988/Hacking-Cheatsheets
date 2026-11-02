# 🚀 ffuf: guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones del original:** Fuzzing significa probar entradas automáticamente; wordlist es una lista de palabras. Se conservan los ejemplos originales. `-r` sigue redirecciones: no selecciona un resolutor DNS. Los valores de tiempo de `-ft` y `-mt` requieren la sintaxis de comparación admitida por la versión; los ejemplos numéricos aislados deben revisarse. `-se` detiene el proceso por errores espurios, no equivale simplemente a detenerse ante un 403. Véase la [ayuda oficial de ffuf](https://github.com/ffuf/ffuf). La URL `v2.x.x` es un marcador de versión. El fuzzing de `Host` descubre hosts virtuales y no confirma registros DNS. `clusterbomb` prueba combinaciones: el título credential stuffing y la comparación de recursividad de Gobuster son simplificaciones del original. La velocidad y las estrellas son valoraciones orientativas.


```
   __  __        __ 
  / _|/ _|_   _ / _|
 | |_| |_| | | | |_ 
 |  _|  _| |_| |  _|
 |_| |_|  \__,_|_|  
                    
   Fuzz Faster U Fool
```

<p align="center">
  <img src="https://img.shields.io/badge/ffuf-Web_Fuzzer-red?style=for-the-badge" alt="ffuf">
  <img src="https://img.shields.io/badge/Fast-Fuzzing-orange?style=for-the-badge" alt="Fast">
  <img src="https://img.shields.io/badge/Bug-Bounty-blue?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Go-Language-green?style=for-the-badge" alt="Go">
</p>

<p align="center">
  <b>⚡ El fuzzer web más rápido escrito en Go</b>
</p>

---

## 📋 Índice

- [Introducción](#introducción)
- [Instalación](#instalación)
- [Sintaxis básica](#sintaxis-básica)
- [Fuzzing de directorios](#fuzzing-de-directorios)
- [Fuzzing de subdominios](#fuzzing-de-subdominios)
- [Fuzzing de parámetros](#fuzzing-de-parámetros)
- [Fuzzing de datos POST](#fuzzing-de-datos-post)
- [Filtrado y selección de coincidencias](#filtrado-y-selección-de-coincidencias)
- [Funciones avanzadas](#funciones-avanzadas)
- [Ejemplos prácticos](#ejemplos-prácticos)
- [Referencia rápida](#referencia-rápida)
- [Consejos y buenas prácticas](#consejos-y-buenas-prácticas)
- [Recursos](#recursos)

---

<a id="introducción"></a>

## 🎯 Introducción

**ffuf** (Fuzz Faster U Fool) es un fuzzer web rápido escrito en Go. Es una herramienta habitual para la enumeración de directorios por fuerza bruta, el descubrimiento de hosts virtuales y el fuzzing de parámetros en programas de recompensas por vulnerabilidades (bug bounty).

### Funciones principales

| Función | Descripción |
|---------|-------------|
| **Velocidad** | Muy rápido (basado en Go) |
| **Flexible** | Probar cualquier parte de una solicitud HTTP mediante fuzzing |
| **Filtrado** | Varias opciones de filtrado y selección de coincidencias |
| **Recursividad** | Admite exploración recursiva |
| **Salida** | Varios formatos de salida |
| **Sencillo** | Sintaxis sencilla e intuitiva |

### ffuf frente a otras herramientas

| Característica | ffuf | Gobuster | dirb |
|---------|------|----------|------|
| **Velocidad** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **Flexibilidad** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **Fuzzing POST** | ✅ Sí | ❌ No | ❌ No |
| **Recursividad** | ✅ Sí | ✅ Sí | ✅ Sí |
| **Filtros** | Muchos | Limitados | Limitados |

---

<a id="instalación"></a>

## 📥 Instalación

### Mediante Go
```bash
go install github.com/ffuf/ffuf/v2@latest
```

### Kali Linux
```bash
sudo apt install ffuf
```

### Descarga del binario
```bash
# Descargar desde las versiones publicadas
https://github.com/ffuf/ffuf/releases

# Linux
wget https://github.com/ffuf/ffuf/releases/download/v2.x.x/ffuf_2.x.x_linux_amd64.tar.gz
tar xzf ffuf_2.x.x_linux_amd64.tar.gz
sudo mv ffuf /usr/local/bin/
```

### Verificar la instalación
```bash
ffuf -V
ffuf -h
```

---

<a id="sintaxis-básica"></a>

## ⌨️ Sintaxis básica

### Sintaxis general
```bash
ffuf [options] -u <URL/FUZZ> -w <wordlist>
```

### La palabra clave FUZZ

La palabra clave **FUZZ** indica a ffuf dónde debe insertar las palabras de la lista de palabras (wordlist).

```bash
# En la ruta de la URL
ffuf -u https://target.com/FUZZ -w wordlist.txt

# En el subdominio
ffuf -u https://FUZZ.target.com -w wordlist.txt

# En el valor de un parámetro
ffuf -u https://target.com/page?id=FUZZ -w wordlist.txt

# En los datos POST
ffuf -u https://target.com/login -X POST -d "user=FUZZ&pass=test" -w wordlist.txt
```

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-u` | URL objetivo con la palabra clave FUZZ |
| `-w` | Ruta de la lista de palabras |
| `-o` | Archivo de salida |
| `-of` | Formato de salida (json, csv, html) |
| `-t` | Número de hilos (valor predeterminado: 40) |
| `-p` | Intervalo entre solicitudes |
| `-r` | Seguir las redirecciones |
| `-v` | Salida detallada |
| `-s` | Modo silencioso |

---

<a id="fuzzing-de-directorios"></a>

## 📁 Fuzzing de directorios

### Fuerza bruta básica de directorios

```bash
# Fuzzing básico de directorios
ffuf -u https://target.com/FUZZ -w /usr/share/wordlists/dirb/common.txt

# Con extensiones de archivo
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.html,.txt,.bak

# Varias extensiones
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.asp,.aspx,.jsp,.html
```

### Exploración recursiva

```bash
# Activar la recursividad
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion

# Profundidad de recursividad
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2

# Recursividad con estrategia
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-strategy greedy
```

### Filtrar por código de estado

```bash
# Seleccionar solo respuestas 200
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200

# Seleccionar varios códigos
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc 200,301,302,403

# Excluir los códigos 404 (cambio del comportamiento predeterminado)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404

# Seleccionar todo y excluir 404
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Listas de palabras habituales

```bash
# SecLists
/usr/share/seclists/Discovery/Web-Content/directory-list-2.3-medium.txt
/usr/share/seclists/Discovery/Web-Content/common.txt
/usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt

# Dirb
/usr/share/wordlists/dirb/common.txt
/usr/share/wordlists/dirb/big.txt
```

---

<a id="fuzzing-de-subdominios"></a>

## 🌐 Fuzzing de subdominios

### Descubrimiento de hosts virtuales

```bash
# Fuzzing básico de subdominios
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt

# Filtrar por tamaño de respuesta (eliminar la página predeterminada)
ffuf -u https://target.com -H "Host: FUZZ.target.com" -w subdomains.txt -fs 1234

# Con una lista de palabras específica
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

### Fuzzing de subdominios DNS

```bash
# Subdominio en la URL
ffuf -u https://FUZZ.target.com -w subdomains.txt

# Con un resolutor (descripción del original)
ffuf -u https://FUZZ.target.com -w subdomains.txt -r
```

### Listas de palabras habituales de subdominios

```bash
# SecLists DNS
/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
/usr/share/seclists/Discovery/DNS/subdomains-top1million-20000.txt
/usr/share/seclists/Discovery/DNS/namelist.txt
```

---

<a id="fuzzing-de-parámetros"></a>

## 🔍 Fuzzing de parámetros

### Fuzzing de parámetros GET

```bash
# Probar el nombre del parámetro mediante fuzzing
ffuf -u "https://target.com/page?FUZZ=test" -w params.txt

# Probar el valor del parámetro mediante fuzzing
ffuf -u "https://target.com/page?id=FUZZ" -w values.txt

# Varios parámetros
ffuf -u "https://target.com/page?W1=test&W2=test" -w params.txt:W1 -w values.txt:W2
```

### Descubrimiento de parámetros

```bash
# Encontrar parámetros ocultos
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

---

<a id="fuzzing-de-datos-post"></a>

## 📝 Fuzzing de datos POST

### Fuzzing POST básico

```bash
# POST con datos de formulario
ffuf -u https://target.com/login -X POST \
    -d "username=FUZZ&password=test" \
    -w usernames.txt

# POST con el tipo de contenido
ffuf -u https://target.com/login -X POST \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "username=FUZZ&password=test" \
    -w usernames.txt
```

### Fuzzing de POST con JSON

```bash
# Datos POST en JSON
ffuf -u https://target.com/api/login -X POST \
    -H "Content-Type: application/json" \
    -d '{"username":"FUZZ","password":"test"}' \
    -w usernames.txt
```

### Varias listas de palabras (credential stuffing)

```bash
# Fuzzing de nombres de usuario y contraseñas
ffuf -u https://target.com/login -X POST \
    -d "username=USER&password=PASS" \
    -w users.txt:USER \
    -w passwords.txt:PASS \
    -fc 401

# Modo: clusterbomb (todas las combinaciones)
ffuf -u https://target.com/login -X POST \
    -d "user=USER&pass=PASS" \
    -w users.txt:USER \
    -w passes.txt:PASS \
    -mode clusterbomb
```

---

<a id="filtrado-y-selección-de-coincidencias"></a>

## 🎯 Filtrado y selección de coincidencias

### Opciones de filtrado

| Opción | Descripción | Ejemplo |
|--------|-------------|---------|
| `-fc` | Excluir por código de estado | `-fc 404,403` |
| `-fs` | Excluir por tamaño de respuesta | `-fs 1234` |
| `-fw` | Excluir por número de palabras | `-fw 100` |
| `-fl` | Excluir por número de líneas | `-fl 50` |
| `-ft` | Excluir por tiempo (ms) | `-ft 5000` |
| `-fr` | Excluir mediante una expresión regular | `-fr "error"` |

### Opciones de selección de coincidencias

| Opción | Descripción | Ejemplo |
|--------|-------------|---------|
| `-mc` | Seleccionar códigos de estado | `-mc 200,301` |
| `-ms` | Seleccionar por tamaño de respuesta | `-ms 1234` |
| `-mw` | Seleccionar por número de palabras | `-mw 100` |
| `-ml` | Seleccionar por número de líneas | `-ml 50` |
| `-mt` | Seleccionar por tiempo (ms) | `-mt 5000` |
| `-mr` | Seleccionar mediante una expresión regular | `-mr "success"` |

### Ejemplos de filtrado

```bash
# Excluir 404 y 403
ffuf -u https://target.com/FUZZ -w wordlist.txt -fc 404,403

# Filtrar por tamaño (eliminar la página predeterminada)
ffuf -u https://target.com/FUZZ -w wordlist.txt -fs 0,1234

# Filtrar por número de palabras
ffuf -u https://target.com/FUZZ -w wordlist.txt -fw 10

# Seleccionar todo y excluir valores específicos
ffuf -u https://target.com/FUZZ -w wordlist.txt -mc all -fc 404
```

### Calibración automática

```bash
# Calibrar los filtros automáticamente
ffuf -u https://target.com/FUZZ -w wordlist.txt -ac

# Calibración con una palabra clave específica
ffuf -u https://target.com/FUZZ -w wordlist.txt -acc "randomstring123"
```

---

<a id="funciones-avanzadas"></a>

## ⚡ Funciones avanzadas

### Limitación de la tasa de solicitudes

```bash
# Solicitudes por segundo
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 100

# Intervalo entre solicitudes
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.5

# Intervalo aleatorio (jitter)
ffuf -u https://target.com/FUZZ -w wordlist.txt -p 0.1-1.0
```

### Hilos

```bash
# Establecer los hilos (valor predeterminado: 40)
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 100

# Reducir para disminuir la visibilidad
ffuf -u https://target.com/FUZZ -w wordlist.txt -t 10 -p 1
```

### Cabeceras

```bash
# Cabecera personalizada
ffuf -u https://target.com/FUZZ -w wordlist.txt -H "X-Custom: value"

# Varias cabeceras
ffuf -u https://target.com/FUZZ -w wordlist.txt \
    -H "Authorization: Bearer token" \
    -H "X-Forwarded-For: 127.0.0.1"

# Cookie
ffuf -u https://target.com/FUZZ -w wordlist.txt -b "session=abc123"
```

### Proxy

```bash
# Proxy HTTP
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080

# Burp Suite
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080
```

### Salida

```bash
# Salida JSON
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.json -of json

# Salida CSV
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.csv -of csv

# Salida HTML
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results.html -of html

# Todos los formatos
ffuf -u https://target.com/FUZZ -w wordlist.txt -o results -of all
```

### Tiempo de espera y reintentos

```bash
# Tiempo de espera de la solicitud
ffuf -u https://target.com/FUZZ -w wordlist.txt -timeout 10

# Detener ante 403 (descripción del original)
ffuf -u https://target.com/FUZZ -w wordlist.txt -se

# Ignorar errores SSL
ffuf -u https://target.com/FUZZ -w wordlist.txt -k
```

---

<a id="ejemplos-prácticos"></a>

## 🎬 Ejemplos prácticos

### Ejemplo 1: exploración básica de directorios
```bash
ffuf -u https://target.com/FUZZ -w /usr/share/seclists/Discovery/Web-Content/common.txt -mc 200,301,302
```

### Ejemplo 2: encontrar archivos PHP
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -e .php,.php.bak,.php~ -mc 200
```

### Ejemplo 3: enumeración de subdominios
```bash
ffuf -u https://target.com -H "Host: FUZZ.target.com" \
    -w /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt \
    -fs 1234 -t 100
```

### Ejemplo 4: descubrimiento de endpoints de API
```bash
ffuf -u https://api.target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/api/api-endpoints.txt \
    -mc 200,401,403
```

### Ejemplo 5: descubrimiento de parámetros
```bash
ffuf -u "https://target.com/page?FUZZ=test" \
    -w /usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt \
    -fs 1234
```

### Ejemplo 6: fuerza bruta de inicio de sesión
```bash
ffuf -u https://target.com/login -X POST \
    -d "user=admin&pass=FUZZ" \
    -w /usr/share/seclists/Passwords/Common-Credentials/10k-most-common.txt \
    -fc 401 -t 10
```

### Ejemplo 7: con proxy (Burp)
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -x http://127.0.0.1:8080 -k
```

### Ejemplo 8: exploración recursiva
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -recursion -recursion-depth 2 -mc 200,301
```

### Ejemplo 9: exploración con tasa limitada
```bash
ffuf -u https://target.com/FUZZ -w wordlist.txt -rate 10 -t 5
```

### Ejemplo 10: exploración completa para bug bounty
```bash
ffuf -u https://target.com/FUZZ \
    -w /usr/share/seclists/Discovery/Web-Content/raft-large-directories.txt \
    -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak \
    -mc 200,301,302,403 \
    -recursion -recursion-depth 2 \
    -o results.json -of json \
    -ac
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Exploración de directorios | `ffuf -u URL/FUZZ -w wordlist.txt` |
| Con extensiones | `ffuf -u URL/FUZZ -w wordlist.txt -e .php,.html` |
| Exploración de subdominios | `ffuf -u URL -H "Host: FUZZ.domain" -w subs.txt` |
| Fuzzing POST | `ffuf -u URL -X POST -d "param=FUZZ" -w wordlist.txt` |
| Excluir 404 | `ffuf -u URL/FUZZ -w wordlist.txt -fc 404` |
| Filtrar por tamaño | `ffuf -u URL/FUZZ -w wordlist.txt -fs 1234` |
| Calibrar automáticamente | `ffuf -u URL/FUZZ -w wordlist.txt -ac` |
| Guardar la salida | `ffuf -u URL/FUZZ -w wordlist.txt -o out.json -of json` |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-u` | URL con FUZZ |
| `-w` | Lista de palabras |
| `-e` | Extensiones |
| `-t` | Hilos |
| `-mc` | Códigos que se seleccionarán |
| `-fc` | Códigos que se excluirán |
| `-fs` | Tamaño que se excluirá |
| `-ac` | Calibrar automáticamente |
| `-r` | Seguir las redirecciones |
| `-o` | Archivo de salida |

---

<a id="consejos-y-buenas-prácticas"></a>

## 💡 Consejos y buenas prácticas

### Consejos para bug bounty

1. **Calibrar automáticamente antes de empezar**
   ```bash
   ffuf -u target.com/FUZZ -w wordlist.txt -ac
   ```

2. **Usar varias extensiones**
   ```bash
   -e .php,.asp,.aspx,.jsp,.html,.js,.txt,.bak,.old,.zip
   ```

3. **Probar extensiones habituales de copias de seguridad**
   ```bash
   -e .bak,.old,.orig,.backup,~,.swp
   ```

4. **Limitar la tasa para evadir el WAF**
   ```bash
   -rate 10 -t 5 -p 0.5
   ```

### Consejos de rendimiento

```bash
# Velocidad alta (sin WAF)
ffuf -u URL/FUZZ -w wordlist.txt -t 200

# Poca concurrencia y baja velocidad (menor visibilidad)
ffuf -u URL/FUZZ -w wordlist.txt -t 10 -rate 5 -p 1
```

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** ffuf es una herramienta potente de fuzzing que solo debe utilizarse en **pruebas autorizadas**.
> 
> - ✅ Usar en programas para los que se tenga permiso de prueba
> - ✅ Usar en programas autorizados de bug bounty
> - ✅ Respetar los límites de tasa y el alcance
> - ❌ No realizar fuzzing sin autorización
> - ❌ No provocar una denegación de servicio
> 
> **Las exploraciones sin autorización son ilegales.**

---

<a id="recursos"></a>

## 📚 Recursos

### Recursos oficiales
- [ffuf GitHub](https://github.com/ffuf/ffuf)
- [ffuf Wiki](https://github.com/ffuf/ffuf/wiki)

### Listas de palabras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [Assetnote Wordlists](https://wordlists.assetnote.io/)

### Guías rápidas relacionadas
- [Nuclei](../../Nuclei/translations/README.es.md)
- [Gobuster](../../Gobuster/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)

---

<p align="center">
  <b>🚀 ¡Haz fuzzing más rápido y encuentra más!</b><br>
  <i>El fuzzer preferido de quienes buscan vulnerabilidades en bug bounty</i>
</p>
