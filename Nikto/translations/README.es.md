# 🕷️ Nikto - Guía rápida completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Escáner de servidores web - Desde 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerabilidad">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Escáner de servidores web de código abierto para pruebas de seguridad</b>
</p>

---

## 📋 Índice

- [Introducción](#-introducción)
- [Instalación](#-instalación)
- [Sintaxis básica](#-sintaxis-básica)
- [Especificación del objetivo](#-especificación-del-objetivo)
- [Opciones de puertos y SSL](#-opciones-de-puertos-y-ssl)
- [Selección de pruebas del escaneo](#-selección-de-pruebas-del-escaneo)
- [Autenticación](#-autenticación)
- [Proxy y evasión](#-proxy-y-evasión)
- [Salida e informes](#-salida-e-informes)
- [Actualización de las bases de datos](#-actualización-de-las-bases-de-datos)
- [Ejemplos prácticos](#-ejemplos-prácticos)
- [Referencia rápida](#-referencia-rápida)
- [Consejos y buenas prácticas](#-consejos-y-buenas-prácticas)
- [Recursos](#-recursos)

---

## 🎯 Introducción

**Nikto** es un escáner de servidores web de código abierto que realiza pruebas exhaustivas sobre distintos aspectos, entre ellos:

### Qué comprueba Nikto

| Categoría | Descripción |
|----------|-------------|
| **Archivos peligrosos** | Archivos que pueden suponer un riesgo (copias de seguridad, etc.) |
| **Versiones obsoletas** | Software de servidor obsoleto |
| **Problemas de versiones concretas** | Vulnerabilidades de versiones concretas |
| **Configuración del servidor** | Configuraciones incorrectas |
| **Scripts CGI** | Scripts CGI potencialmente peligrosos |
| **SSL/TLS** | Problemas con los certificados SSL |
| **Cabeceras** | Cabeceras HTTP ausentes o inseguras |
| **Métodos** | Métodos HTTP peligrosos |

### Estadísticas de las bases de datos

```
✅ 6,700+ archivos/CGI potencialmente peligrosos
✅ 1,250+ versiones de servidor obsoletas
✅ 270+ problemas de versiones concretas
✅ 1,000+ problemas de configuración de servidores
```

> **Nota de versión:** las cifras anteriores son estadísticas históricas del original; no representan un recuento verificado de la versión instalada.

### Funciones

| Función | Descripción |
|---------|-------------|
| **Cobertura amplia** | Pruebas de miles de vulnerabilidades |
| **Basado en plugins** | Arquitectura ampliable |
| **Varios formatos** | Salida HTML, XML, CSV y JSON |
| **Compatibilidad con SSL** | Pruebas completas de HTTPS |
| **Evasión de IDS** | Técnicas de evasión integradas |
| **Autenticación** | Compatibilidad con Basic, Digest y NTLM |

---

## 📥 Instalación

### Kali Linux (preinstalado)
```bash
# Actualizar Nikto
sudo apt update && sudo apt install nikto

# Actualizar la base de datos
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### Desde GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Comprobar la instalación
```bash
nikto -Version
nikto -h          # Ayuda
```

---

<a id="-sintaxis-básica"></a>
## ⌨️ Sintaxis básica

### Sintaxis general
```bash
nikto -h <host> [options]
```

### Inicio rápido
```bash
# Escaneo básico
nikto -h http://target.com

# Escanear indicando el puerto
nikto -h target.com -p 80

# Escanear HTTPS
nikto -h https://target.com

# Escanear una IP
nikto -h 192.168.1.1
```

---

## 🎯 Especificación del objetivo

### Un único objetivo
```bash
# Por nombre de host
nikto -h target.com

# Por IP
nikto -h 192.168.1.1

# Con protocolo
nikto -h http://target.com
nikto -h https://target.com
```

### Varios objetivos
```bash
# Desde un archivo
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Objetivo con una ruta
```bash
# Escanear una ruta concreta
nikto -h http://target.com/app

# Escanear un directorio concreto
nikto -h http://target.com/admin/
```

---

## 🔌 Opciones de puertos y SSL

### Opciones de puertos
```bash
# Un solo puerto
nikto -h target.com -p 80

# Varios puertos
nikto -h target.com -p 80,443,8080

# Rango de puertos
nikto -h target.com -p 80-90

# Puertos web habituales
nikto -h target.com -p 80,443,8000,8080,8443
```

### Opciones de SSL/TLS

| Opción | Descripción |
|--------|-------------|
| `-ssl` | Forzar el modo SSL |
| `-nossl` | Desactivar SSL |
| `-maxtime` | Tiempo máximo de escaneo |

```bash
# Forzar SSL
nikto -h target.com -ssl

# Desactivar SSL (solo HTTP)
nikto -h target.com -nossl

# SSL en un puerto no estándar
nikto -h target.com -p 8443 -ssl
```

### Hosts virtuales
```bash
# Especificar el host virtual
nikto -h 192.168.1.1 -vhost target.com

# Útil cuando:
# - El objetivo está detrás de un balanceador de carga
# - Hay varios sitios en una misma IP
```

---

<a id="-selección-de-pruebas-del-escaneo"></a>
## 🎚️ Selección de pruebas del escaneo

### Opciones de selección de pruebas (-Tuning)

| Código | Descripción |
|------|-------------|
| `0` | Subida de archivos |
| `1` | Archivo de interés / observado en registros |
| `2` | Configuración incorrecta / archivo predeterminado |
| `3` | Divulgación de información |
| `4` | Inyección (XSS/script/HTML) |
| `5` | Obtención remota de archivos dentro de la raíz web |
| `6` | Denegación de servicio |
| `7` | Obtención remota de archivos en todo el servidor |
| `8` | Ejecución de comandos / shell remota |
| `9` | Inyección SQL |
| `a` | Elusión de autenticación |
| `b` | Identificación de software |
| `c` | Inclusión remota de código fuente |
| `d` | Servicio web |
| `e` | Consola de administración |
| `x` | Invertir las opciones de selección de pruebas |

### Uso de la selección de pruebas
```bash
# Realizar solo pruebas de inyección SQL
nikto -h target.com -Tuning 9

# Realizar pruebas de XSS e inyección SQL
nikto -h target.com -Tuning 49

# Probar varios tipos
nikto -h target.com -Tuning 1234

# Excluir ciertas pruebas (prefijo x)
nikto -h target.com -Tuning x6    # Excluir las pruebas de DoS

# Todas excepto DoS
nikto -h target.com -Tuning x6
```

### Plugins
```bash
# Listar los plugins disponibles
nikto -list-plugins

# Ejecutar un plugin concreto
nikto -h target.com -Plugins apacheusers

# Ejecutar varios plugins
nikto -h target.com -Plugins "apacheusers;robots"

# Excluir plugins
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Plugins habituales

| Plugin | Descripción |
|--------|-------------|
| `apacheusers` | Enumeración de usuarios de Apache |
| `cgi` | Pruebas de scripts CGI |
| `robots` | Análisis de robots.txt |
| `headers` | Análisis de cabeceras HTTP |
| `httpoptions` | Pruebas de métodos HTTP |
| `outdated` | Comprobación de software obsoleto |
| `ssl` | Análisis de SSL/TLS |
| `dictionary` | Ataque de diccionario |

---

## 🔐 Autenticación

### Autenticación Basic
```bash
# Nombre de usuario y contraseña
nikto -h target.com -id admin:password

# Formato Base64
nikto -h target.com -id "admin:password"
```

> **Corrección:** las comillas de `"admin:password"` no convierten las credenciales en Base64; solo agrupan el argumento del shell. El formato de `-id` es usuario:contraseña, con un tercer componente opcional de realm.

### Autenticación Digest
```bash
# Autenticación Digest (detección automática)
nikto -h target.com -id admin:password
```

### Autenticación NTLM
```bash
# Autenticación NTLM
nikto -h target.com -id admin:password:domain
```

### Autenticación mediante cookies
```bash
# Enviar cookies
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Desde un archivo
nikto -h target.com -cookies cookies.txt
```

---

> **Compatibilidad de cookies:** los ejemplos con `-cookies` se conservan del original, pero esa opción no figura en la [lista oficial de opciones consultada](https://github.com/sullo/nikto/wiki/Annotated-Option-List). Comprueba la configuración de cookies y cabeceras de tu versión antes de reutilizarlos.

<a id="-proxy-y-evasión"></a>
## 🛡️ Proxy y evasión

### Compatibilidad con proxies
```bash
# Proxy HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy autenticado
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Mediante Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### Evasión de IDS (-evasion)

| Código | Técnica |
|------|-----------|
| `1` | Codificación aleatoria de URI (no UTF-8) |
| `2` | Autorreferencia de directorio (/./) |
| `3` | Terminación prematura de URL |
| `4` | Anteponer una cadena aleatoria larga |
| `5` | Parámetro falso |
| `6` | TAB como separador en la petición |
| `7` | Cambiar mayúsculas y minúsculas de la URL |
| `8` | Utilizar el separador de directorios de Windows (\) |
| `A` | Utilizar un retorno de carro (0x0d) |
| `B` | Utilizar el valor binario 0x0b |

```bash
# Una sola técnica de evasión
nikto -h target.com -evasion 1

# Varias técnicas de evasión
nikto -h target.com -evasion 1234

# Todas las técnicas de evasión
nikto -h target.com -evasion 12345678AB
```

> **Alcance:** las opciones de evasión alteran las peticiones, pero no garantizan un escaneo indetectable. La tabla conserva las técnicas del original; las opciones disponibles pueden variar entre versiones.

### User-Agent
```bash
# User-Agent personalizado
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Temporización de las peticiones
```bash
# Pausa entre peticiones (segundos)
nikto -h target.com -Pause 2

# Tiempo máximo de escaneo (segundos)
nikto -h target.com -maxtime 3600

# Tiempo de espera por petición
nikto -h target.com -timeout 10
```

---

## 📄 Salida e informes

### Formatos de salida

| Opción | Formato |
|--------|--------|
| `-o file` | Guardar la salida en un archivo |
| `-Format htm` | Formato HTML |
| `-Format xml` | Formato XML |
| `-Format csv` | Formato CSV |
| `-Format txt` | Formato de texto |
| `-Format json` | Formato JSON |
| `-Format nbe` | Formato NBE |
| `-Format sql` | Formato SQL |

### Guardar los resultados
```bash
# Salida de texto
nikto -h target.com -o report.txt

# Informe HTML
nikto -h target.com -o report.html -Format htm

# Informe XML
nikto -h target.com -o report.xml -Format xml

# Informe JSON
nikto -h target.com -o report.json -Format json

# Informe CSV
nikto -h target.com -o report.csv -Format csv
```

### Opciones de detalle de la salida
```bash
# Opciones de visualización
nikto -h target.com -Display V    # Salida detallada
nikto -h target.com -Display D    # Depuración
nikto -h target.com -Display E    # Errores HTTP
nikto -h target.com -Display P    # Progreso
nikto -h target.com -Display S    # Ocultar las IP en la salida
nikto -h target.com -Display 1    # Mostrar las redirecciones
nikto -h target.com -Display 2    # Mostrar las cookies
nikto -h target.com -Display 3    # Mostrar las respuestas 200/OK
nikto -h target.com -Display 4    # Mostrar las URL que requieren autenticación

# Varias opciones de visualización
nikto -h target.com -Display VEP
```

### Guardar el progreso

> **Corrección:** `-Save` guarda respuestas positivas en un directorio; no crea un punto de reanudación. `-Resume` no figura en la lista oficial consultada. Los comandos siguientes se conservan como referencia del original. Véase la [documentación oficial de guardado y reproducción](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-%26-Replaying).
```bash
# Guardar el estado del escaneo (según el original, permite reanudarlo)
nikto -h target.com -Save scan_state.txt

# Reanudar el escaneo
nikto -Resume scan_state.txt
```

---

## 🔄 Actualización de las bases de datos

> **Compatibilidad:** `-update` y las descargas bajo `2.1.5` pertenecen al flujo histórico del original. Las versiones y distribuciones actuales pueden emplear otro mecanismo; consulta la [guía oficial de actualización](https://github.com/sullo/nikto/wiki/Updating).

### Actualizar la base de datos de Nikto
```bash
# Actualizar todas las bases de datos
nikto -update

# Actualización manual (si falla -update)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Ubicación de las bases de datos
```bash
# Ubicaciones predeterminadas
/usr/share/nikto/databases/
/var/lib/nikto/

# Archivos de las bases de datos
db_tests         # Base de datos principal de pruebas
db_outdated      # Versiones de software obsoletas
db_server_msgs   # Mensajes del servidor
db_variables     # Variables
db_404_strings   # Cadenas de páginas 404
```

---

## 🎬 Ejemplos prácticos

### Ejemplo 1: escaneo básico de un servidor web
```bash
nikto -h http://target.com
```

### Ejemplo 2: escaneo completo de HTTPS
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Ejemplo 3: escaneo de varios puertos
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Ejemplo 4: escaneo autenticado
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Ejemplo 5: escaneo denominado stealth (evasión de IDS)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Ejemplo 6: escaneo mediante el proxy de Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Ejemplo 7: pruebas centradas en inyección SQL
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Ejemplo 8: auditoría completa
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Ejemplo 9: pruebas de hosts virtuales
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Ejemplo 10: escaneo desde un archivo de objetivos
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Referencia rápida

### Comandos esenciales

| Tarea | Comando |
|------|---------|
| Escaneo básico | `nikto -h target.com` |
| Escaneo HTTPS | `nikto -h target.com -ssl` |
| Varios puertos | `nikto -h target.com -p 80,443,8080` |
| Con autenticación | `nikto -h target.com -id user:pass` |
| Informe HTML | `nikto -h target.com -o report.html -Format htm` |
| Evasión | `nikto -h target.com -evasion 123` |
| Mediante proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Actualizar la base de datos | `nikto -update` |

### Opciones habituales

| Opción | Descripción |
|--------|-------------|
| `-h` | Host objetivo |
| `-p` | Puerto o puertos que se escanearán |
| `-ssl` | Forzar el modo SSL |
| `-id` | Credenciales de autenticación |
| `-o` | Archivo de salida |
| `-Format` | Formato de salida |
| `-Tuning` | Selección de pruebas del escaneo |
| `-evasion` | Evasión de IDS |
| `-useproxy` | Servidor proxy |
| `-vhost` | Host virtual |
| `-Pause` | Espera entre pruebas |
| `-timeout` | Tiempo de espera de las peticiones |

> **Precisión de la tabla:** los rangos siguientes son un resumen aproximado del original. Consulta la tabla completa de `-Tuning` de esta guía para el significado de cada código; por ejemplo, `4` corresponde a inyección y no solo a información o configuración.

### Referencia rápida de selección de pruebas

| Código | Categoría |
|------|----------|
| 1-4 | Información y configuración incorrecta |
| 4-5 | Inyección y obtención de archivos |
| 6 | DoS (evitar en producción) |
| 7-8 | Acceso remoto |
| 9 | Inyección SQL |
| a | Elusión de autenticación |
| b | Identificación del software |
| x | Excluir |

---

## 💡 Consejos y buenas prácticas

### Consejos de rendimiento

1. **Comenzar con un escaneo básico**
   ```bash
   nikto -h target.com
   ```

2. **Excluir las pruebas de DoS**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Utilizar tiempos de espera adecuados**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Consejos para mejorar la precisión

1. **Actualizar la base de datos con regularidad**
   ```bash
   nikto -update
   ```

2. **Utilizar el modo detallado**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Combinar con otras herramientas**
   ```bash
   # Primero Nmap, después Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Problemas habituales

| Problema | Solución |
|-------|----------|
| Escaneo lento | Reducir `-Pause`, aumentar `-timeout` |
| Errores SSL | Utilizar `-ssl` explícitamente |
| Problemas de autenticación | Comprobar el formato de `-id` |
| Faltan resultados | Actualizar la base de datos y comprobar los plugins |

---

## ⚠️ Aviso legal

> **ADVERTENCIA:** Nikto es un escáner de servidores web que debe utilizarse únicamente en **pruebas autorizadas**.
> 
> - ✅ Escanear servidores propios
> - ✅ Escanear con autorización expresa por escrito
> - ✅ Utilizar en pruebas de penetración autorizadas
> - ❌ No escanear sin autorización
> - ❌ El escaneo no autorizado es ilegal
> 
> **Nikto puede generar un volumen considerable de tráfico y activar alertas de seguridad.**

---

## 📚 Recursos

### Recursos oficiales
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Documentación de Nikto](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Recursos de aprendizaje
- [Guía de pruebas de OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guías rápidas relacionadas
- [Nmap](../../Nmap/translations/README.es.md)
- [Gobuster](../../Gobuster/translations/README.es.md)
- [Burp Suite](../../Burp-Suite/translations/README.es.md)

---

<p align="center">
  <b>🕷️ ¡Domina el escaneo de servidores web!</b><br>
  <i>Encuentra las vulnerabilidades antes que los atacantes</i>
</p>
